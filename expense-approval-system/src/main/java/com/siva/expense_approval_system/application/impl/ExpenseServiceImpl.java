package com.siva.expense_approval_system.application.impl;

import java.math.BigDecimal;
import java.util.Comparator;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.siva.expense_approval_system.application.service.ExpenseService;
import com.siva.expense_approval_system.domain.model.ApprovalChain;
import com.siva.expense_approval_system.domain.model.Expense;
import com.siva.expense_approval_system.domain.model.ExpenseStatus;
import com.siva.expense_approval_system.domain.repository.ApprovalChainRepository;
import com.siva.expense_approval_system.domain.repository.ExpenseRepository;

@Service
public class ExpenseServiceImpl implements ExpenseService{

    private static final Logger log = LoggerFactory.getLogger(ExpenseServiceImpl.class);

     private final ExpenseRepository expenseRepository;

     private final ApprovalChainRepository approvalChainRepository;

     public ExpenseServiceImpl(ExpenseRepository expenseRepository, ApprovalChainRepository approvalChainRepository){
        this.expenseRepository = expenseRepository;
        this.approvalChainRepository = approvalChainRepository;
     }
     
     @Override
     public Expense createExpense(Expense expense){
        return expenseRepository.save(expense);
     }
     @Override
     public Expense submitExpense(Expense expense){

      if(expense.getAmount().compareTo(BigDecimal.ZERO) <= 0){
          throw new IllegalArgumentException("Amount must greater than Zero");
      }

      if(expense.getDescription() == null || expense.getDescription().trim().isEmpty()){
         throw new IllegalArgumentException("Description Cannot be Empty");
      }
       List<ApprovalChain> approvalChains = getApprovalChains(expense);

      ApprovalChain initialChain = approvalChains.stream()
              .min(Comparator.comparingInt(ApprovalChain::getStepOrder))
              .orElseThrow(() -> new IllegalArgumentException("No approval chain configured for this expense."));

      log.debug("submitExpense amount={}, tenantId={}, approvalChains={}, initialStep={}",
              expense.getAmount(),
              expense.getTenant() != null ? expense.getTenant().getId() : null,
              approvalChains.stream()
                      .map(chain -> "step=" + chain.getStepOrder() + ",min=" + chain.getMinAmount() + ",max=" + chain.getMaxAmount())
                      .toList(),
              initialChain.getStepOrder());

      expense.setStatus(ExpenseStatus.PENDING);
      expense.setCurrentApprovalStep(initialChain.getStepOrder());
      return expenseRepository.save(expense);
     }

     @Override
     public Expense ApproveExpense(Expense expense) {
        validatePendingExpense(expense);

        // Fetch ALL approval chains for this tenant ordered by step so the workflow advances
        // through the configured chain rather than skipping the first approval step.
        List<ApprovalChain> approvalChains = getApprovalChains(expense);

        log.debug("approveExpense expenseId={}, tenantId={}, currentApprovalStep={}, approvalChains={}",
                expense.getId(),
                expense.getTenant() != null ? expense.getTenant().getId() : null,
                expense.getCurrentApprovalStep(),
                approvalChains.stream()
                        .map(chain -> "step=" + chain.getStepOrder() + ",min=" + chain.getMinAmount() + ",max=" + chain.getMaxAmount())
                        .toList());

        ApprovalChain currentChain = approvalChains.stream()
                .filter(chain -> chain.getStepOrder().equals(expense.getCurrentApprovalStep()))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException(
                        "No approval chain is configured for step " + expense.getCurrentApprovalStep() + "."));

        ApprovalChain nextChain = approvalChains.stream()
                .filter(chain -> chain.getStepOrder() > currentChain.getStepOrder())
                .findFirst()
                .orElse(null);

        log.debug("approveExpense currentChainStep={}, nextChainStep={}"
                , currentChain.getStepOrder(), nextChain != null ? nextChain.getStepOrder() : null);

        if (nextChain == null) {
            expense.setStatus(ExpenseStatus.APPROVED);
        } else {
            expense.setCurrentApprovalStep(nextChain.getStepOrder());
        }

        return expenseRepository.save(expense);
     }

     @Override
     public Expense RejectExpense(Expense expense) {
        validatePendingExpense(expense);
        expense.setStatus(ExpenseStatus.REJECTED);
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

     private List<ApprovalChain> getApprovalChains(Expense expense) {
        List<ApprovalChain> approvalChains =
                approvalChainRepository.findByTenantOrderByStepOrderAsc(expense.getTenant());

        if (approvalChains.isEmpty()) {
            throw new IllegalArgumentException("No approval chain configured for this expense.");
        }

        return approvalChains;
     }
     
     //if the expense is already approved or rejected it throws an error it is used to check the expense is approved or rejected
     private void validatePendingExpense(Expense expense) {
        if (expense.getStatus() != ExpenseStatus.PENDING) {
            throw new IllegalArgumentException("Only pending expenses can be approved or rejected.");
        }
     }
}
