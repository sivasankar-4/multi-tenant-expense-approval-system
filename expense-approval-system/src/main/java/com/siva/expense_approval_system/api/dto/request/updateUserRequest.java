package com.siva.expense_approval_system.api.dto.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public class UpdateUserRequest {
    
     @NotBlank(message = "name is required")
     private String name;
     
     @Email(message = "Invalid Email format")
     @NotBlank(message = "email is required")
     private String email;
     
     @NotBlank(message ="role is required")
     private String role;

     public String getName() {
         return name;
     }

     public void setName(String name) {
         this.name = name;
     }

     public String getEmail() {
         return email;
     }

     public void setEmail(String email) {
         this.email = email;
     }

     public String getRole() {
         return role;
     }

     public void setRole(String role) {
         this.role = role;
     }




}
