package com.siva.expense_approval_system.application.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.siva.expense_approval_system.domain.model.ApprovalChain;
import com.siva.expense_approval_system.domain.model.Expense;
import com.siva.expense_approval_system.domain.model.ExpenseStatus;
import com.siva.expense_approval_system.domain.model.Tenant;
import com.siva.expense_approval_system.domain.model.User;
import com.siva.expense_approval_system.domain.repository.ApprovalChainRepository;
import com.siva.expense_approval_system.domain.repository.ExpenseRepository;

@ExtendWith(MockitoExtension.class)
class ExpenseServiceImplTest {

    @Mock
    private ExpenseRepository expenseRepository;

    @Mock
    private ApprovalChainRepository approvalChainRepository;

    @InjectMocks
    private ExpenseServiceImpl expenseService;

    private Tenant tenant;
    private User submittedBy;

    @BeforeEach
    void setUp() {
        tenant = new Tenant();
        tenant.setId(1L);
        tenant.setName("Contoso");

        submittedBy = new User();
        submittedBy.setId(10L);
        submittedBy.setName("Alice");
    }

    @Test
    void submitExpenseShouldStartAtTheFirstConfiguredApprovalStep() {
        Expense expense = new Expense(
                99L,
                tenant,
                submittedBy,
                new BigDecimal("80000"),
                "USD",
                "Travel",
                "Team trip",
                ExpenseStatus.PENDING,
                1,
                LocalDateTime.now()
        );

        ApprovalChain stepOne = new ApprovalChain();
        stepOne.setStepOrder(1);
        stepOne.setMinAmount(BigDecimal.ZERO);
        stepOne.setMaxAmount(new BigDecimal("50000"));

        ApprovalChain stepTwo = new ApprovalChain();
        stepTwo.setStepOrder(2);
        stepTwo.setMinAmount(new BigDecimal("50001"));
        stepTwo.setMaxAmount(new BigDecimal("100000"));

        when(approvalChainRepository.findByTenantOrderByStepOrderAsc(tenant)).thenReturn(List.of(stepOne, stepTwo));
        when(expenseRepository.save(any(Expense.class))).thenAnswer(invocation -> invocation.getArgument(0));

        Expense submittedExpense = expenseService.submitExpense(expense);

        assertEquals(ExpenseStatus.PENDING, submittedExpense.getStatus());
        assertEquals(1, submittedExpense.getCurrentApprovalStep());
    }
}
