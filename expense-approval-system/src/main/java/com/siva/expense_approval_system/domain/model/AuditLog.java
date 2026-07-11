package com.siva.expense_approval_system.domain.model;

import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "audit_log")
public class AuditLog {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long Id;
    
    @ManyToOne
    @JoinColumn(name = "tenant_id",nullable = false)
    private Tenant tenant;
    
    @Column(name = "actor_id",nullable = false)
    private Long actorId;
    
    @Column(name = "action_type",nullable = false)
    private String ActionType;
    
    @Column(name = "entity_type",nullable = false)
    private String EntityType;
   
    @Column(name = "entity_id",nullable = false)
    private Long EntityId;
    
    @Column(columnDefinition = "TEXT")
    private String metaData;
    
    @Column(name ="created_at",nullable = false)
    private LocalDateTime createdAt;



    public AuditLog(){

    }



    public AuditLog(Long id, Tenant tenant, Long actorId, String actionType, String entityType, Long entityId,
            String metaData, LocalDateTime createdAt) {
        Id = id;
        this.tenant = tenant;
        this.actorId = actorId;
        ActionType = actionType;
        EntityType = entityType;
        EntityId = entityId;
        this.metaData = metaData;
        this.createdAt = createdAt;
    }



    public Long getId() {
        return Id;
    }



    public void setId(Long id) {
        Id = id;
    }



    public Tenant getTenant() {
        return tenant;
    }



    public void setTenant(Tenant tenant) {
        this.tenant = tenant;
    }



    public Long getActorId() {
        return actorId;
    }



    public void setActorId(Long actorId) {
        this.actorId = actorId;
    }



    public String getActionType() {
        return ActionType;
    }



    public void setActionType(String actionType) {
        ActionType = actionType;
    }



    public String getEntityType() {
        return EntityType;
    }



    public void setEntityType(String entityType) {
        EntityType = entityType;
    }



    public Long getEntityId() {
        return EntityId;
    }



    public void setEntityId(Long entityId) {
        EntityId = entityId;
    }



    public String getMetaData() {
        return metaData;
    }



    public void setMetaData(String metaData) {
        this.metaData = metaData;
    }



    public LocalDateTime getCreatedAt() {
        return createdAt;
    }



    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    
}


