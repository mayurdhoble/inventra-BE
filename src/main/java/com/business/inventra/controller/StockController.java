package com.business.inventra.controller;

import com.business.inventra.dto.request.StockRequest;
import com.business.inventra.dto.response.StockListResponse;
import com.business.inventra.dto.response.StockResponse;
import com.business.inventra.dto.response.StockAuditListResponse;
import com.business.inventra.dto.response.ApiResponse;
import com.business.inventra.service.StockService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/organizations/")
public class StockController {

    @Autowired
    private StockService stockService;

    @GetMapping("/{orgId}/branch/{branchId}/stocks")
    public ResponseEntity<ApiResponse<StockListResponse>> getAllStocks(
            @PathVariable String orgId,
            @PathVariable String branchId,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        StockListResponse response = stockService.getAllStocks(orgId, branchId, page, size);
        return ResponseEntity.ok(ApiResponse.success("Stocks retrieved successfully", response));
    }

    @GetMapping("/{orgId}/branch/{branchId}/stocks/audit")
    public ResponseEntity<ApiResponse<StockAuditListResponse>> getStockAudits(
            @PathVariable String orgId,
            @PathVariable String branchId,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        StockAuditListResponse response = stockService.getStockAudits(orgId, branchId, page, size);
        return ResponseEntity.ok(ApiResponse.success("Stock audit history retrieved successfully", response));
    }

    @PostMapping("/{orgId}/stock")
    public ResponseEntity<ApiResponse<StockResponse>> updateStock(
            @RequestBody StockRequest request,
            @PathVariable String orgId) {
    	request.setOrgId(orgId);
        StockResponse response = stockService.updateStock(request);
        return ResponseEntity.ok(ApiResponse.success("Stock updated successfully", response));
    }
} 