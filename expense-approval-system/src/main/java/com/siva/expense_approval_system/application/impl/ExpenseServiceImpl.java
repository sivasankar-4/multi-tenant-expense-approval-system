package com.siva.expense_approval_system.application.impl;

import java.util.List;

import org.springframework.stereotype.Service;

import com.siva.expense_approval_system.application.service.ExpenseService;
import com.siva.expense_approval_system.domain.model.Expense;
import com.siva.expense_approval_system.domain.repository.ExpenseRepository;

@Service
public class ExpenseServiceImpl implements ExpenseService{
    

     private final ExpenseRepository expenseRepository;

     public ExpenseServiceImpl(ExpenseRepository expenseRepository){
        this.expenseRepository = expenseRepository;
     }
     
     @Override
     public Expense createExpense(Expense expense){
        return expenseRepository.save(expense);
     }

     @Override
     public Expense getExpenseById(Long id) {
        return expenseRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Expense not found: " + id));
     }

     @Override
     public List<Expense> getAllExpenses() {
        return expenseRepository.findAll();
     }

     @Override
     public Expense updateExpense(Long id, Expense expense) {
        Expense existingExpense = getExpenseById(id);
        existingExpense.setTenant(expense.getTenant());
        existingExpense.setSubmittedBy(expense.getSubmittedBy());
        existingExpense.setAmount(expense.getAmount());
        existingExpense.setCurrency(expense.getCurrency());
        existingExpense.setCategory(expense.getCategory());
        existingExpense.setDescription(expense.getDescription());
        existingExpense.setStatus(expense.getStatus());
        existingExpense.setCurrentApprovalStep(expense.getCurrentApprovalStep());
        return expenseRepository.save(existingExpense);
     }

     @Override
     public void deleteExpense(Long id) {
        expenseRepository.delete(getExpenseById(id));
     }
}
