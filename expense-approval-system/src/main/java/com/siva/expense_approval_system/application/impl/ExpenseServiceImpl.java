package com.siva.expense_approval_system.application.impl;

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
        return ExpenseRepository.save(expense);
     }

     
}
