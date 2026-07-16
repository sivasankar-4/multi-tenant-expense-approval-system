package com.siva.expense_approval_system.api.mapper;

import com.siva.expense_approval_system.api.dto.request.CreateUserRequest;
import com.siva.expense_approval_system.api.dto.request.UpdateUserRequest;
import com.siva.expense_approval_system.api.dto.response.UserResponse;
import com.siva.expense_approval_system.domain.model.Tenant;
import com.siva.expense_approval_system.domain.model.User;
import com.siva.expense_approval_system.domain.model.UserRole;

public class UserMapper {

     public User toEntityMapping(CreateUserRequest request ,Tenant tenant){

         User user = new User();

         user.setTenant(tenant);
         user.setName(request.getName());
         user.setEmail(request.getEmail());
         user.setPassword(request.getPassword());
         if(request.getRole() != null){
             user.setRole(UserRole.valueOf(request.getRole()));
         }

         return user;
     }

     public void updateEntity(UpdateUserRequest request ,User user){

         user.setName(request.getName());
         user.setEmail(request.getEmail());
          if(request.getRole() != null){
             user.setRole(UserRole.valueOf(request.getRole()));
         }
     }

     public UserResponse toResponse(User user){

        UserResponse response = new UserResponse();


        response.setId(user.getId());
        response.setName(user.getName());
        response.setEmail(user.getEmail());
        if(user.getRole() != null){
             response.setRole(user.getRole().name());
        }
        if(user.getTenant() != null){
            response.setTenantid(user.getTenant().getId());
        }

        return response;
     }

}
