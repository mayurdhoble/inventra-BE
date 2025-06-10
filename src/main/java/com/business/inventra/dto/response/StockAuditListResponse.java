package com.business.inventra.dto.response;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

public class StockAuditListResponse {
    private List<StockAuditDetails> audits;
    private long totalElements;
    private int totalPages;
    private int currentPage;
    private int pageSize;

    public List<StockAuditDetails> getAudits() {
        return audits;
    }

    public void setAudits(List<StockAuditDetails> audits) {
        this.audits = audits;
    }

    public long getTotalElements() {
        return totalElements;
    }

    public void setTotalElements(long totalElements) {
        this.totalElements = totalElements;
    }

    public int getTotalPages() {
        return totalPages;
    }

    public void setTotalPages(int totalPages) {
        this.totalPages = totalPages;
    }

    public int getCurrentPage() {
        return currentPage;
    }

    public void setCurrentPage(int currentPage) {
        this.currentPage = currentPage;
    }

    public int getPageSize() {
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }

    public static class StockAuditDetails {
        private String id;
        private String pCode;
        private String pName;
        private String pCategory;
        private String pCategoryName;
        private String branchId;
        private String branchName;
        private String pUnit;
        private String unitDesc;
        private BigDecimal quantity;
        private String operationType;
        private LocalDateTime operationDate;
        private String operatorName;
        private String invoiceNumber;
        private String remarks;
        private String userId;
        private String orgId;
        private BigDecimal costPrice;
        private BigDecimal sellingPrice;
        private String receiverId;
        private String receiverName;

        // Getters and Setters
        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getPCode() {
            return pCode;
        }

        public void setPCode(String pCode) {
            this.pCode = pCode;
        }

        public String getPName() {
            return pName;
        }

        public void setPName(String pName) {
            this.pName = pName;
        }

        public String getPCategory() {
            return pCategory;
        }

        public void setPCategory(String pCategory) {
            this.pCategory = pCategory;
        }

        public String getPCategoryName() {
            return pCategoryName;
        }

        public void setPCategoryName(String pCategoryName) {
            this.pCategoryName = pCategoryName;
        }

        public String getBranchId() {
            return branchId;
        }

        public void setBranchId(String branchId) {
            this.branchId = branchId;
        }

        public String getBranchName() {
            return branchName;
        }

        public void setBranchName(String branchName) {
            this.branchName = branchName;
        }

        public String getPUnit() {
            return pUnit;
        }

        public void setPUnit(String pUnit) {
            this.pUnit = pUnit;
        }

        public String getUnitDesc() {
            return unitDesc;
        }

        public void setUnitDesc(String unitDesc) {
            this.unitDesc = unitDesc;
        }

        public BigDecimal getQuantity() {
            return quantity;
        }

        public void setQuantity(BigDecimal quantity) {
            this.quantity = quantity;
        }

        public String getOperationType() {
            return operationType;
        }

        public void setOperationType(String operationType) {
            this.operationType = operationType;
        }

        public LocalDateTime getOperationDate() {
            return operationDate;
        }

        public void setOperationDate(LocalDateTime operationDate) {
            this.operationDate = operationDate;
        }

        public String getOperatorName() {
            return operatorName;
        }

        public void setOperatorName(String operatorName) {
            this.operatorName = operatorName;
        }

        public String getInvoiceNumber() {
            return invoiceNumber;
        }

        public void setInvoiceNumber(String invoiceNumber) {
            this.invoiceNumber = invoiceNumber;
        }

        public String getRemarks() {
            return remarks;
        }

        public void setRemarks(String remarks) {
            this.remarks = remarks;
        }

        public String getUserId() {
            return userId;
        }

        public void setUserId(String userId) {
            this.userId = userId;
        }

        public String getOrgId() {
            return orgId;
        }

        public void setOrgId(String orgId) {
            this.orgId = orgId;
        }

        public BigDecimal getCostPrice() {
            return costPrice;
        }

        public void setCostPrice(BigDecimal costPrice) {
            this.costPrice = costPrice;
        }

        public BigDecimal getSellingPrice() {
            return sellingPrice;
        }

        public void setSellingPrice(BigDecimal sellingPrice) {
            this.sellingPrice = sellingPrice;
        }

        public String getReceiverId() {
            return receiverId;
        }

        public void setReceiverId(String receiverId) {
            this.receiverId = receiverId;
        }

        public String getReceiverName() {
            return receiverName;
        }

        public void setReceiverName(String receiverName) {
            this.receiverName = receiverName;
        }
    }
} 