package com.siva.expense_approval_system.api.dto.request;

import java.math.BigDecimal;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

public class CreateExpenseRequest{
     
    @NotNull(message ="tenant id is required")
    private Long tenantId;
    
    @NotNull(message = "submitted user is required")
    private Long submittedbyId;
 
    @Positive(message = "amount must be greater than zero")
    private BigDecimal amount;

    @NotBlank(message = "currency is required")
    private String currency;

    @NotBlank(message = "category is required")
    private String category;

    @NotBlank(message = "description is required")
    private String description;

    public Long getTenantId() {
        return tenantId;
    }

    public void setTenantId(Long tenantId) {
        this.tenantId = tenantId;
    }

    public Long getSubmittedbyId() {
        return submittedbyId;
    }

    public void setSubmittedbyId(Long submittedbyId) {
        this.submittedbyId = submittedbyId;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    
     
}
