package com.business.inventra.dto.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import lombok.Data;

@Data
public class StockRequest {
    @NotBlank(message = "Product code is required")
    @JsonProperty("pCode")
    private String pCode;

    @NotBlank(message = "Product unit is required")
    @JsonProperty("pUnit")
    private String pUnit;

    @NotNull(message = "Quantity is required")
    @Positive(message = "Quantity must be positive")
    @JsonProperty("pQuantity")
    private BigDecimal pQuantity;

    @NotBlank(message = "Branch ID is required")
    @JsonProperty("branchId")
    private String branchId;

    @NotBlank(message = "User ID is required")
    @JsonProperty("userId")
    private String userId;

    @NotBlank(message = "Operation type is required")
    @JsonProperty("operationType")
    private String operationType;

    @JsonProperty("invoiceNumber")
    private String invoiceNumber;

    @JsonProperty("remark")
    private String remark;

    @JsonProperty("orgId")
    private String orgId;

    @NotNull(message = "Cost price per unit is required")
    @Positive(message = "Cost price must be positive")
    @JsonProperty("costPrice")
    private BigDecimal costPrice;

    
    @Positive(message = "Selling price must be positive")
    @JsonProperty("sellingPrice")
    private BigDecimal sellingPrice;


    @JsonProperty("receiverId")
    private String receiverId;

    // Getters and Setters
    public String getPCode() {
        return pCode;
    }

    public void setPCode(String pCode) {
        this.pCode = pCode;
    }

    public String getPUnit() {
        return pUnit;
    }

    public void setPUnit(String pUnit) {
        this.pUnit = pUnit;
    }

    public BigDecimal getPQuantity() {
        return pQuantity;
    }

    public void setPQuantity(BigDecimal pQuantity) {
        this.pQuantity = pQuantity;
    }

    public String getBranchId() {
        return branchId;
    }

    public void setBranchId(String branchId) {
        this.branchId = branchId;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getOperationType() {
        return operationType;
    }

    public void setOperationType(String operationType) {
        this.operationType = operationType;
    }

    public String getInvoiceNumber() {
        return invoiceNumber;
    }

    public void setInvoiceNumber(String invoiceNumber) {
        this.invoiceNumber = invoiceNumber;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
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
} 