package com.siva.expense_approval_system.application.impl;

import java.math.BigDecimal;
import java.util.Comparator;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Service;

import com.siva.expense_approval_system.application.service.ExpenseService;
import com.siva.expense_approval_system.domain.enums.ExpenseStatus;
import com.siva.expense_approval_system.domain.model.ApprovalChain;
import com.siva.expense_approval_system.domain.model.Expense;
import com.siva.expense_approval_system.domain.repository.ApprovalChainRepository;
import com.siva.expense_approval_system.domain.repository.ExpenseRepository;
import com.siva.expense_approval_system.infrastructure.security.CurrentUserService;

@Service
public class ExpenseServiceImpl implements ExpenseService{

    private static final Logger log = LoggerFactory.getLogger(ExpenseServiceImpl.class);

     private final ExpenseRepository expenseRepository;

     private final ApprovalChainRepository approvalChainRepository;
     private final CurrentUserService currentUserService;

     public ExpenseServiceImpl(ExpenseRepository expenseRepository, ApprovalChainRepository approvalChainRepository,
             CurrentUserService currentUserService){
        this.expenseRepository = expenseRepository;
        this.approvalChainRepository = approvalChainRepository;
        this.currentUserService = currentUserService;
     }
     
     @Override
     public Expense createExpense(Expense expense){
        expense.setStatus(ExpenseStatus.PENDING);
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
        Expense tenantExpense = getExpenseById(expense.getId());
        validatePendingExpense(tenantExpense);
       

        //this loads the configured approval chain for the expenses tenant
        List<ApprovalChain> approvalChains = getApprovalChains(tenantExpense);

        log.debug("approveExpense expenseId={}, tenantId={}, currentApprovalStep={}, approvalChains={}",
                tenantExpense.getId(),
                tenantExpense.getTenant() != null ? tenantExpense.getTenant().getId() : null,
                tenantExpense.getCurrentApprovalStep(),
                approvalChains.stream()
                        .map(chain -> "step=" + chain.getStepOrder() + ",min=" + chain.getMinAmount() + ",max=" + chain.getMaxAmount())
                        .toList());
       
         // it finds where the expense currently is it simply finds the current chain
        ApprovalChain currentChain = approvalChains.stream()
                .filter(chain -> chain.getStepOrder().equals(tenantExpense.getCurrentApprovalStep()))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException(
                        "No approval chain is configured for step " + tenantExpense.getCurrentApprovalStep() + "."));
       //it asks is there any step greater than 1 if it is step2 finance
        //so nextchain = step2
        ApprovalChain nextChain = approvalChains.stream()
                .filter(chain -> chain.getStepOrder() > currentChain.getStepOrder())
                .findFirst()
                .orElse(null);

        log.debug("approveExpense currentChainStep={}, nextChainStep={}"
                , currentChain.getStepOrder(), nextChain != null ? nextChain.getStepOrder() : null);
        
         // then it tells if the nextchain == null then it shows as approved if it is not null then goes to the next step
        if (nextChain == null) { //if finish workflow it set as null and status as approved
            tenantExpense.setStatus(ExpenseStatus.APPROVED);
        } else {
            tenantExpense.setCurrentApprovalStep(nextChain.getStepOrder());
        }

        return expenseRepository.save(tenantExpense);
     }

     @Override
     public Expense RejectExpense(Expense expense) {
        Expense tenantExpense = getExpenseById(expense.getId());
        validatePendingExpense(tenantExpense);
        tenantExpense.setStatus(ExpenseStatus.REJECTED);
        return expenseRepository.save(tenantExpense);
     }

     @Override
     public Expense getExpenseById(Long id) {
        return expenseRepository.findByIdAndTenantId(id, getCurrentTenantId())
                .orElseThrow(() -> new AccessDeniedException("Expense not found or does not belong to the current tenant."));
     }

     @Override
     public List<Expense> getAllExpenses() {
        return expenseRepository.findAllByTenantId(getCurrentTenantId());
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

     private Long getCurrentTenantId() {
        if (currentUserService.getCurrentTenant() == null || currentUserService.getCurrentTenant().getId() == null) {
            throw new AccessDeniedException("Current user is not associated with a tenant.");
        }
        return currentUserService.getCurrentTenant().getId();
     }
}
