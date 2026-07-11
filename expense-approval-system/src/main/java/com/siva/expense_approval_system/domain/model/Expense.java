package com.siva.expense_approval_system.domain.model;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

@Entity

@Table(name ="expenses")

public class Expense {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @ManyToOne
    @JoinColumn(name = "tenant_id",nullable = false)
    private Tenant tenant;

    @ManyToOne
    @JoinColumn(name = "submitted_by",nullable = false)
    private User submittedBy;


   @NotNull
    @Column(nullable = false)
    private BigDecimal amount;

    @NotBlank
    @Column(nullable = false)
    private String currency;

    @NotBlank
    @Column(nullable = false)
    private String category;

    @Column(length = 1000)
    private String description;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private ExpenseStatus status;

    @Column(name = "current_approval_step", nullable = false)
    private Integer currentApprovalStep;                                                                                                                                                                                    
                                
    @Column(name = "created_at", nullable = false)                                    
    private LocalDateTime createdAt;                
                                 
    public Expense(){                                 
                 
    }             

    public Expense(Long id ,Tenant tenant, User submittedBy, @NotNull BigDecimal amount, @NotBlank String currency,
            @NotBlank String category, String description, ExpenseStatus status, Integer currentApprovalStep,
            LocalDateTime createdAt) {                         
        this.id = id;
        this.tenant = tenant;
        this.submittedBy = submittedBy;                                                                                                                                                                   
        this.amount = amount;
        this.currency = currency;
        this.category = category;
        this.description = description;
        this.status = status;
        this.currentApprovalStep = currentApprovalStep;
        this.createdAt = createdAt;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Tenant getTenant() {
        return tenant;
    }

    public void setTenant(Tenant tenant) {
        this.tenant = tenant;
    }

    public User getSubmittedBy() {
        return submittedBy;
    }

    public void setSubmittedBy(User submittedBy) {
        this.submittedBy = submittedBy;
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

    public ExpenseStatus getStatus() {
        return status;
    }

    public void setStatus(ExpenseStatus status) {
        this.status = status;
    }

    public Integer getCurrentApprovalStep() {
        return currentApprovalStep;
    }

    public void setCurrentApprovalStep(Integer currentApprovalStep) {
        this.currentApprovalStep = currentApprovalStep;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    

}
