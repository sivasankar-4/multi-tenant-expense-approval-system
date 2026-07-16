package com.siva.expense_approval_system.api.dto.request;

import java.math.BigDecimal;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

public class CreateExpenseRequest{
     
     @NotNull(message ="tenant id is required")
    private Long tenantId;
    
    @NotNull(message = "submitted user is required")
    private Long submittedbyId;
 
    @Positive(message = "amount must be greater than zero")
    private BigDecimal amount;
    
    @Positive(message = "Description is required")
    private String Description;

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

    public String getDescription() {
        return Description;
    }

    public void setDescription(String description) {
        Description = description;
    }

    
     
}