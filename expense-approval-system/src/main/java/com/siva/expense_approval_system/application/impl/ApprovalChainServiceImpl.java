package com.siva.expense_approval_system.application.impl;

import java.util.List;

import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Service;

import com.siva.expense_approval_system.application.service.ApprovalChainService;
import com.siva.expense_approval_system.domain.model.ApprovalChain;
import com.siva.expense_approval_system.domain.repository.ApprovalChainRepository;
import com.siva.expense_approval_system.infrastructure.security.CurrentUserService;

@Service
public class ApprovalChainServiceImpl implements ApprovalChainService {
    
      private final ApprovalChainRepository approvalChainRepository;
      private final CurrentUserService currentUserService;

      public ApprovalChainServiceImpl(ApprovalChainRepository approvalChainRepository,
            CurrentUserService currentUserService){

        this.approvalChainRepository = approvalChainRepository;
        this.currentUserService = currentUserService;
      }

      @Override
      public ApprovalChain createApprovalChain(ApprovalChain approvalChain) {
        validateAmountRange(approvalChain);
        approvalChain.setTenant(currentUserService.getCurrentTenant());
        return approvalChainRepository.save(approvalChain);
      }

      @Override
      public ApprovalChain getApprovalChainById(Long id) {
        return approvalChainRepository.findByIdAndTenantId(id, getCurrentTenantId())
            .orElseThrow(() -> new AccessDeniedException("Approval chain not found or does not belong to the current tenant."));
      }

      @Override
      public List<ApprovalChain> getAllApprovalChains() {
        return approvalChainRepository.findAllByTenantId(getCurrentTenantId());
      }

      @Override
      public ApprovalChain updateApprovalChain(Long id, ApprovalChain approvalChain) {
        validateAmountRange(approvalChain);

        ApprovalChain existingChain = getApprovalChainById(id);
        existingChain.setMinAmount(approvalChain.getMinAmount());
        existingChain.setMaxAmount(approvalChain.getMaxAmount());
        existingChain.setStepOrder(approvalChain.getStepOrder());
        existingChain.setApproverRole(approvalChain.getApproverRole());
        return approvalChainRepository.save(existingChain);
      }

      @Override
      public void deleteApprovalChain(Long id) {
        approvalChainRepository.delete(getApprovalChainById(id));
      }

      private void validateAmountRange(ApprovalChain approvalChain) {
        if (approvalChain.getMinAmount().compareTo(approvalChain.getMaxAmount()) > 0) {
          throw new IllegalArgumentException("Minimum amount cannot be greater than maximum amount.");
        }
      }

      private Long getCurrentTenantId() {
        if (currentUserService.getCurrentTenant() == null || currentUserService.getCurrentTenant().getId() == null) {
          throw new AccessDeniedException("Current user is not associated with a tenant.");
        }
        return currentUserService.getCurrentTenant().getId();
      }
}
