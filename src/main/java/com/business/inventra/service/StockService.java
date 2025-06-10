package com.business.inventra.service;

import com.business.inventra.dto.request.StockRequest;
import com.business.inventra.dto.response.ProductDetails;
import com.business.inventra.dto.response.StockListResponse;
import com.business.inventra.dto.response.StockResponse;
import com.business.inventra.dto.response.UnitDetails;
import com.business.inventra.dto.response.StockAuditListResponse;
import com.business.inventra.exception.ResourceNotFoundException;
import com.business.inventra.model.*;
import com.business.inventra.repository.*;
import com.business.inventra.util.KeyGenerator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class StockService {

    @Autowired
    private StockMaintenanceRepository stockMaintenanceRepository;

    @Autowired
    private StockAuditRepository stockAuditRepository;

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private BranchRepository branchRepository;

    @Autowired
    private UnitRepository unitRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private OrganizationRepository organizationRepository;

    /* Product Pricing - Commented out for now
    @Autowired
    private ProductPricingRepository productPricingRepository;
    */

    @Autowired
    private UserBranchMappingRepository userBranchRepository;

    @Autowired
    private ProductUnitDetailsRepository productUnitDetailsRepository;

    @Transactional
    public StockResponse updateStock(StockRequest request) {
        // Validate organization
        Organization organization = organizationRepository.findById(request.getOrgId())
                .orElseThrow(() -> new ResourceNotFoundException("Organization not found"));

        // Validate product
        Product product = productRepository.findBypCodeAndOrgId(request.getPCode(), request.getOrgId())
                .orElseThrow(() -> new ResourceNotFoundException("Product not found"));

        // Validate branch
        Branch branch = branchRepository.findByIdAndOrgID(request.getBranchId(), request.getOrgId())
                .orElseThrow(() -> new ResourceNotFoundException("Branch not found"));

        // Validate unit - only check by unit_code
        Unit unit = unitRepository.findById(request.getPUnit())
                .orElseThrow(() -> new ResourceNotFoundException("Unit not found with code: " + request.getPUnit()));

        // Validate user and check branch access
        User user = userRepository.findById(request.getUserId())
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));

        // Check if user has access to the branch
        boolean hasBranchAccess = userBranchRepository.existsByUserIdAndBranchId(request.getUserId(), request.getBranchId());
        if (!hasBranchAccess) {
            throw new ResourceNotFoundException("User does not have access to the specified branch");
        }

        /* Product Pricing - Commented out for now
        // Check if product pricing already exists for this combination
        boolean pricingExists = productPricingRepository.existsBypCodeAndpUnitAndEffectiveDate(
            request.getPCode(), 
            request.getPUnit(), 
            request.getEffectiveDate()
        );

        if (pricingExists) {
            throw new ResourceNotFoundException("Cannot add price for the stock as pricing already exists for this product, unit, and effective date");
        }

        // Create or update product pricing
        ProductPricing pricing = new ProductPricing();
        pricing.setPCode(request.getPCode());
        pricing.setPUnit(request.getPUnit());
        pricing.setPricePerUnit(request.getPPrice());
        pricing.setEffectiveDate(request.getEffectiveDate());
        pricing.setIsDefault(true);
        pricing.setOrgId(request.getOrgId());
        productPricingRepository.save(pricing);
        */

        // Create or update product unit details
        boolean productUnitExists = productUnitDetailsRepository.existsBypCodeAndpUnitAndOrgId(
            request.getPCode(), 
            request.getPUnit(),
            request.getOrgId()
        );

        if (!productUnitExists) {
            ProductUnitDetails newDetails = new ProductUnitDetails();
            newDetails.setPCode(request.getPCode());
            newDetails.setPUnit(request.getPUnit());
            newDetails.setDefault(true);
            newDetails.setProduct(product);
            newDetails.setUnit(unit);
            newDetails.setOrgId(request.getOrgId());
            productUnitDetailsRepository.save(newDetails);
        }

        // Get or create stock maintenance
        StockMaintenance stockMaintenance = stockMaintenanceRepository
                .findByPCodeAndBranchIdAndPUnitAndOrgId(
                        request.getPCode(),
                        request.getBranchId(),
                        request.getPUnit(),
                        request.getOrgId())
                .orElseGet(() -> {
                    StockMaintenance newStock = new StockMaintenance();
                    newStock.setPCode(request.getPCode());
                    newStock.setBranchId(request.getBranchId());
                    newStock.setPUnit(request.getPUnit());
                    newStock.setOrgId(request.getOrgId());
                    newStock.setCurrentStock(BigDecimal.ZERO);
                    return newStock;
                });

        // Update stock
        BigDecimal currentStock = stockMaintenance.getCurrentStock();
        BigDecimal newStock = request.getOperationType().equalsIgnoreCase("IN")
                ? currentStock.add(request.getPQuantity())
                : currentStock.subtract(request.getPQuantity());

        if (newStock.compareTo(BigDecimal.ZERO) < 0) {
            throw new RuntimeException("Insufficient stock");
        }

        stockMaintenance.setCurrentStock(newStock);
        stockMaintenance.setLastUpdatedAt(LocalDateTime.now());
        stockMaintenanceRepository.save(stockMaintenance);

        // Create stock audit
        StockAudit stockAudit = new StockAudit();
        stockAudit.setPCode(request.getPCode());
        stockAudit.setBranchId(request.getBranchId());
        stockAudit.setPUnit(request.getPUnit());
        stockAudit.setQuantity(request.getPQuantity());
        stockAudit.setOperationType(StockAudit.OperationType.valueOf(request.getOperationType()));
        stockAudit.setOperationDate(LocalDateTime.now());
        stockAudit.setOperatorName(user.getFullName());
        stockAudit.setInvoiceNumber(request.getInvoiceNumber());
        stockAudit.setUserId(request.getUserId());
        stockAudit.setRemarks(request.getRemark());
        stockAudit.setOrgId(request.getOrgId());
        stockAuditRepository.save(stockAudit);

        // Create response
        StockResponse response = new StockResponse();
        response.setBranchId(request.getBranchId());
        response.setCurrentStock(newStock);
        response.setLastUpdatedAt(stockMaintenance.getLastUpdatedAt());
        response.setOperationType(request.getOperationType());
        response.setQuantity(request.getPQuantity());
        response.setOperatorName(user.getFullName());
        response.setInvoiceNumber(request.getInvoiceNumber());
        response.setRemarks(request.getRemark());

        // Set product details
        ProductDetails productDetails = new ProductDetails();
        productDetails.setPCode(product.getPCode());
        productDetails.setPName(product.getPName());
        productDetails.setPCategory(product.getPCatCode());
        productDetails.setPCategoryName(product.getProductCategory().getpCatName());
        response.setProduct(productDetails);

        // Set unit details
        UnitDetails unitDetails = new UnitDetails();
        unitDetails.setId(unit.getId());
        unitDetails.setUnitCode(unit.getUnitCode());
        unitDetails.setUnitDesc(unit.getUnitDesc());
        response.setUnit(unitDetails);

        return response;
    }

    @Transactional(readOnly = true)
    public StockListResponse getAllStocks(String orgId, String branchId, int page, int size) {
        // Validate organization
        Organization organization = organizationRepository.findById(orgId)
                .orElseThrow(() -> new ResourceNotFoundException("Organization not found"));

        // Validate branch
        Branch branch = branchRepository.findByIdAndOrgID(branchId, orgId)
                .orElseThrow(() -> new ResourceNotFoundException("Branch not found"));

        // Get paginated stock maintenance records for the branch
        Pageable pageable = PageRequest.of(page, size);
        Page<StockMaintenance> stockPage = stockMaintenanceRepository.findByOrgIdAndBranchId(orgId, branchId, pageable);

        // Create response
        StockListResponse response = new StockListResponse();
        response.setTotalElements(stockPage.getTotalElements());
        response.setTotalPages(stockPage.getTotalPages());
        response.setCurrentPage(stockPage.getNumber());
        response.setPageSize(stockPage.getSize());

        // Convert to response DTO
        List<StockListResponse.StockDetails> stockDetails = stockPage.getContent().stream()
                .map(stock -> {
                    StockListResponse.StockDetails details = new StockListResponse.StockDetails();
                    details.setId(stock.getId());
                    details.setPCode(stock.getPCode());
                    details.setBranchId(stock.getBranchId());
                    details.setPUnit(stock.getPUnit());
                    details.setCurrentStock(stock.getCurrentStock());
                    details.setLastUpdatedAt(stock.getLastUpdatedAt());
                    details.setThresholdLevel(stock.getThresholdLevel());
                    details.setOrgId(stock.getOrgId());

                    // Get product details
                    Product product = productRepository.findBypCodeAndOrgId(stock.getPCode(), orgId)
                            .orElse(null);
                    if (product != null) {
                        details.setPName(product.getPName());
                        details.setPCategory(product.getPCatCode());
                        details.setPCategoryName(product.getProductCategory().getpCatName());
                    }

                    // Get branch details
                    details.setBranchName(branch.getName());

                    // Get unit details
                    Unit unit = unitRepository.findById(stock.getPUnit())
                            .orElse(null);
                    if (unit != null) {
                        details.setUnitDesc(unit.getUnitCode());
                    }

                    return details;
                })
                .collect(Collectors.toList());

        response.setStocks(stockDetails);
        return response;
    }

    public StockAuditListResponse getStockAudits(String orgId, String branchId, int page, int size) {
        // Validate organization
        Organization organization = organizationRepository.findById(orgId)
                .orElseThrow(() -> new ResourceNotFoundException("Organization not found"));

        // Validate branch
        Branch branch = branchRepository.findByIdAndOrgID(branchId, orgId)
                .orElseThrow(() -> new ResourceNotFoundException("Branch not found"));

        // Get paginated stock audits
        Pageable pageable = PageRequest.of(page, size);
        Page<StockAudit> stockAudits = stockAuditRepository.findByOrgIdAndBranchIdOrderByOperationDateDesc(orgId, branchId, pageable);

        // Convert to response DTO
        List<StockAuditListResponse.StockAuditDetails> auditDetails = stockAudits.getContent().stream()
                .map(audit -> {
                    StockAuditListResponse.StockAuditDetails details = new StockAuditListResponse.StockAuditDetails();
                    details.setId(audit.getId());
                    details.setPCode(audit.getPCode());
                    details.setBranchId(audit.getBranchId());
                    details.setPUnit(audit.getPUnit());
                    details.setQuantity(audit.getQuantity());
                    details.setOperationType(audit.getOperationType().toString());
                    details.setOperationDate(audit.getOperationDate());
                    details.setOperatorName(audit.getOperatorName());
                    details.setInvoiceNumber(audit.getInvoiceNumber());
                    details.setRemarks(audit.getRemarks());
                    details.setUserId(audit.getUserId());
                    details.setOrgId(audit.getOrgId());
                    details.setCostPrice(audit.getCostPrice());
                    details.setSellingPrice(audit.getSellingPrice());
                    details.setReceiverId(audit.getReceiverId());

                    // Get product details
                    Product product = productRepository.findBypCodeAndOrgId(audit.getPCode(), orgId)
                            .orElse(null);
                    if (product != null) {
                        details.setPName(product.getPName());
                        details.setPCategory(product.getPCatCode());
                        details.setPCategoryName(product.getProductCategory().getpCatName());
                    }

                    // Get branch details
                    details.setBranchName(branch.getName());

                    // Get unit details
                    Unit unit = unitRepository.findById(audit.getPUnit())
                            .orElse(null);
                    if (unit != null) {
                        details.setUnitDesc(unit.getUnitCode());
                    }

                    // Get receiver details if exists
                    if (audit.getReceiverId() != null) {
                        User receiver = userRepository.findById(audit.getReceiverId())
                                .orElse(null);
                        if (receiver != null) {
                            details.setReceiverName(receiver.getFullName());
                        }
                    }

                    return details;
                })
                .collect(Collectors.toList());

        // Create response
        StockAuditListResponse response = new StockAuditListResponse();
        response.setAudits(auditDetails);
        response.setTotalElements(stockAudits.getTotalElements());
        response.setTotalPages(stockAudits.getTotalPages());
        response.setCurrentPage(stockAudits.getNumber());
        response.setPageSize(stockAudits.getSize());

        return response;
    }
} 