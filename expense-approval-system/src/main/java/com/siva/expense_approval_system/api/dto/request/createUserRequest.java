package com.siva.expense_approval_system.api.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public class createUserRequest {
    
    
    @NotNull
    private Long id;
    
    @NotBlank(message =  "name is required")
    private String name;
    
    @NotBlank(message = "email is required")
    private String email;
    
    @NotBlank(message = "password is required")
    private String password;
    
    @NotBlank(message = "role is required")
    private String role;
    
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

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

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }


    
}
