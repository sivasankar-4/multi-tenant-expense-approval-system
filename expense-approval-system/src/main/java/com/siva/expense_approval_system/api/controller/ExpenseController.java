package com.siva.expense_approval_system.api.controller;


import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.siva.expense_approval_system.api.dto.request.CreateExpenseRequest;
import com.siva.expense_approval_system.api.dto.request.UpdateExpenseRequest;
import com.siva.expense_approval_system.api.dto.response.ExpenseResponse;
import com.siva.expense_approval_system.api.mapper.ExpenseMapper;
import com.siva.expense_approval_system.application.service.ExpenseService;
import com.siva.expense_approval_system.application.service.TenantService;
import com.siva.expense_approval_system.application.service.UserService;
import com.siva.expense_approval_system.domain.model.Expense;
import com.siva.expense_approval_system.domain.model.Tenant;
import com.siva.expense_approval_system.domain.model.User;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/expenses")
public class ExpenseController {
    
      private final ExpenseService expenseService;
      private final ExpenseMapper expenseMapper;
      private final TenantService tenantService;
      private final UserService userService;

      public ExpenseController(ExpenseService expenseService, ExpenseMapper expenseMapper ,
                                TenantService tenantService,UserService userService) {
        this.expenseService = expenseService;
        this.expenseMapper = expenseMapper;
        this.tenantService = tenantService;
        this.userService = userService;
      }

      @PostMapping
      public ResponseEntity<ExpenseResponse> createExpense(@RequestBody @Valid CreateExpenseRequest request){
         
        Tenant tenant = tenantService.getTenantById(request.getTenantId());

        User user = userService.getUserById(request.getSubmittedbyId());

        Expense expense = expenseMapper.toEntity(request, tenant, user);

        Expense savedExpense = expenseService.createExpense(expense);

        ExpenseResponse response = expenseMapper.toResponse(savedExpense);

        return ResponseEntity.status(HttpStatus.CREATED).body(response);
         
      }

      @GetMapping
      public ResponseEntity<List<ExpenseResponse>> getAllExpenses(){

         List<Expense> expenses = expenseService.getAllExpenses();

         List<ExpenseResponse> response = expenses.stream().map(expenseMapper :: toResponse).toList();

         return ResponseEntity.ok(response);
      }
      
      @GetMapping("/{id}")
      public ResponseEntity<ExpenseResponse> getExpenseById(@PathVariable Long id){

        Expense expense = expenseService.getExpenseById(id);

        ExpenseResponse response = expenseMapper.toResponse(expense);

        return ResponseEntity.ok(response);
      }
     
      @PutMapping("/{id}")
      public ResponseEntity<ExpenseResponse> updateExpense(@PathVariable Long id, @RequestBody @Valid UpdateExpenseRequest request){
              
        Expense expense = expenseService.getExpenseById(id);

        expenseMapper.updateEntity(request, expense);

        Expense updatedExpense = expenseService.updateExpense(id, expense);

        ExpenseResponse response = expenseMapper.toResponse(updatedExpense);

        return ResponseEntity.ok(response);
         
      }
    
      @DeleteMapping("/{id}")
      public ResponseEntity<Void> deleteExpense(@PathVariable Long id){
             
          expenseService.deleteExpense(id);

          return ResponseEntity.noContent().build();
      }
     
}
