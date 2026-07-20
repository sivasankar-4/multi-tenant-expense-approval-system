package com.siva.expense_approval_system.application.impl;

import java.util.List;

import org.springframework.stereotype.Service;

import com.siva.expense_approval_system.application.service.ApprovalChainService;
import com.siva.expense_approval_system.domain.model.ApprovalChain;
import com.siva.expense_approval_system.domain.repository.ApprovalChainRepository;

@Service
public class ApprovalChainServiceImpl implements ApprovalChainService {
    
      private final ApprovalChainRepository approvalChainRepository;

      public ApprovalChainServiceImpl(ApprovalChainRepository approvalChainRepository){

        this.approvalChainRepository = approvalChainRepository;
      }

      @Override
      public ApprovalChain createApprovalChain(ApprovalChain approvalChain) {
        validateAmountRange(approvalChain);
        return approvalChainRepository.save(approvalChain);
      }

      @Override
      public ApprovalChain getApprovalChainById(Long id) {
        return approvalChainRepository.findById(id)
            .orElseThrow(() -> new IllegalArgumentException("Approval chain not found: " + id));
      }

      @Override
      public List<ApprovalChain> getAllApprovalChains() {
        return approvalChainRepository.findAll();
      }

      @Override
      public ApprovalChain updateApprovalChain(Long id, ApprovalChain approvalChain) {
        validateAmountRange(approvalChain);

        ApprovalChain existingChain = getApprovalChainById(id);
        existingChain.setTenant(approvalChain.getTenant());
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
}
