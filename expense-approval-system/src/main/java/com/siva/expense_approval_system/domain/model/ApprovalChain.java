package com.siva.expense_approval_system.domain.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;

@Entity
@Table(name = "approval_chains")
public class ApprovalChain {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "tenant_id", nullable = false)
    private Tenant tenant;

    @NotNull
    @Column(name = "min_amount", nullable = false)
    private BigDecimal minAmount;

    @NotNull
    @Column(name = "max_amount", nullable = false)
    private BigDecimal maxAmount;

    @Column(name = "step_order", nullable = false)
    private Integer stepOrder;

    @Enumerated(EnumType.STRING)
    @Column(name = "approver_role", nullable = false)
    private Role approverRole;

}
