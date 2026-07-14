package com.siva.expense_approval_system.application.service;

import java.util.List;

import com.siva.expense_approval_system.domain.model.Expense;

public interface ExpenseService {
    
    
    Expense createExpense(Expense expense);

    Expense getExpenseById(Long id);

    List<Expense> getAllExpenses(Long id);

    Expense updateExpense(Long id,Expense expense);

    void deleteExpense(Long id);


}
