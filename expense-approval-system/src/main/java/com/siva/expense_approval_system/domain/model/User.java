package com.siva.expense_approval_system.domain.model;

import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;

@Entity
@Table(name = "users")
public class User {
    
     @Id
     @GeneratedValue(strategy = GenerationType.IDENTITY)
     private Long id;
    
     @ManyToOne
     @JoinColumn(name = "tenant_id",nullable = false)
     private Tenant tenant;
    
     @Column(nullable = false)
     private String Name;
     
     @NotBlank
     @Column(nullable = false)
     private String email;
     
     @NotBlank
     @Column(nullable = false)
     private String password;
       
     @Enumerated(EnumType.STRING)
     private UserRole role;
      
     @Column(name = "created_at",nullable = false)
     private LocalDateTime createdAt;
     
     public User(){

     }

     public User(Long id, Tenant tenant, String Name, @NotBlank String email, @NotBlank String password, UserRole role,
            LocalDateTime createdAt) {
        this.id = id;
        this.tenant = tenant;
        this.Name = Name;
        this.email = email;
        this.password = password;
        this.role = role;
        this.createdAt = createdAt;
     }

     public Long getId() {
         return id;
     }

     public void setId(Long id) {
         this.id = id;
     }

     public Tenant getTenant() {
         return tenant;
     }

     public void setTenant(Tenant tenant) {
         this.tenant = tenant;
     }

     public String getName() {
         return Name;
     }

     public void setName(String Name) {
         this.Name = Name;
     }

     public String getEmail() {
         return email;
     }

     public void setEmail(String email) {
         this.email = email;
     }

     public String getPassword() {
         return password;
     }

     public void setPassword(String password) {
         this.password = password;
     }

     public UserRole getRole() {
         return role;
     }

     public void setRole(UserRole role) {
         this.role = role;
     }

     public LocalDateTime getCreatedAt() {
         return createdAt;
     }

     public void setCreatedAt(LocalDateTime createdAt) {
         this.createdAt = createdAt;
     }

     
     

}
