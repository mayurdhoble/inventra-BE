package com.business.inventra.dto.response;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

public class StockListResponse {
    private List<StockDetails> stocks;
    private long totalElements;
    private int totalPages;
    private int currentPage;
    private int pageSize;

    public List<StockDetails> getStocks() {
        return stocks;
    }

    public void setStocks(List<StockDetails> stocks) {
        this.stocks = stocks;
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

    public static class StockDetails {
        private String id;
        private String pCode;
        private String pName;
        private String pCategory;
        private String pCategoryName;
        private String branchId;
        private String branchName;
        private String pUnit;
        private String unitDesc;
        private BigDecimal currentStock;
        private LocalDateTime lastUpdatedAt;
        private BigDecimal thresholdLevel;
        private String orgId;

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

        public BigDecimal getCurrentStock() {
            return currentStock;
        }

        public void setCurrentStock(BigDecimal currentStock) {
            this.currentStock = currentStock;
        }

        public LocalDateTime getLastUpdatedAt() {
            return lastUpdatedAt;
        }

        public void setLastUpdatedAt(LocalDateTime lastUpdatedAt) {
            this.lastUpdatedAt = lastUpdatedAt;
        }

        public BigDecimal getThresholdLevel() {
            return thresholdLevel;
        }

        public void setThresholdLevel(BigDecimal thresholdLevel) {
            this.thresholdLevel = thresholdLevel;
        }

        public String getOrgId() {
            return orgId;
        }

        public void setOrgId(String orgId) {
            this.orgId = orgId;
        }
    }
} 