package com.siva.expense_approval_system.domain.model;

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

@Entity
@Table(name = "ApprovalAction")
public class ApprovalAction {
    

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long Id;
       
    @ManyToOne
    @JoinColumn(name ="expense_id",nullable = false)
    private Expense expense;
    
    @ManyToOne
    @JoinColumn(name ="approver_id",nullable = false)
    private User approver;
    
    @Enumerated(EnumType.STRING)
    private ApprovalActionStatus action;
    
    @Column(length = 500)
    private String comment;
    
    @Column(name = "acted_at",nullable = false)
    private LocalDateTime actedAt;


    public ApprovalAction(){

    }


    public ApprovalAction(Long id, Expense expense, User approver, ApprovalActionStatus action, String comment,
            LocalDateTime actedAt) {
        Id = id;
        this.expense = expense;
        this.approver = approver;
        this.action = action;
        this.comment = comment;
        this.actedAt = actedAt;
    }


    public Long getId() {
        return Id;
    }


    public void setId(Long id) {
        Id = id;
    }


    public Expense getExpense() {
        return expense;
    }


    public void setExpense(Expense expense) {
        this.expense = expense;
    }


    public User getApprover() {
        return approver;
    }


    public void setApprover(User approver) {
        this.approver = approver;
    }


    public ApprovalActionStatus getAction() {
        return action;
    }


    public void setAction(ApprovalActionStatus action) {
        this.action = action;
    }


    public String getComment() {
        return comment;
    }


    public void setComment(String comment) {
        this.comment = comment;
    }


    public LocalDateTime getActedAt() {
        return actedAt;
    }


    public void setActedAt(LocalDateTime actedAt) {
        this.actedAt = actedAt;
    }

    

}
