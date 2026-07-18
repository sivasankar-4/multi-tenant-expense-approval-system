package com.siva.expense_approval_system.api.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.siva.expense_approval_system.api.dto.request.CreateUserRequest;
import com.siva.expense_approval_system.api.dto.request.UpdateUserRequest;
import com.siva.expense_approval_system.api.dto.response.UserResponse;
import com.siva.expense_approval_system.api.mapper.UserMapper;
import com.siva.expense_approval_system.application.service.TenantService;
import com.siva.expense_approval_system.application.service.UserService;
import com.siva.expense_approval_system.domain.model.Tenant;
import com.siva.expense_approval_system.domain.model.User;

import jakarta.validation.Valid;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PutMapping;




@RestController
@RequestMapping("/api/users")

public class UserController {

   private final UserService userService;

   private final UserMapper userMapper;

   private final TenantService tenantService;

   public UserController(UserService userService,TenantService tenantService,UserMapper userMapper) {
           
           this.userService = userService;
           this.tenantService = tenantService;
           this.userMapper = userMapper;
   }
    
   @PostMapping
   /* Sample JSON for Postman POST /api/users
    {
      "tenantId": 1,
      "name": "Jane Doe",
      "email": "jane.doe@example.com",
      "password": "P@ssw0rd123",
      "role": "EMPLOYEE"
    }
   */
   public ResponseEntity<UserResponse> createUser(@Valid @RequestBody CreateUserRequest request){

        Tenant tenant = tenantService.getTenantById(request.getTenantId());

        User user = userMapper.toEntityMapping(request, tenant);
        
        User savedUser = userService.createUser(user);

        UserResponse response = userMapper.toResponse(savedUser);

        return ResponseEntity.status(HttpStatus.CREATED).body(response);
   }

     @GetMapping
     public ResponseEntity<List<UserResponse>> getAllUsers() {

         List<User> users = userService.getAllUsers();

         List<UserResponse> response = users.stream().map(userMapper :: toResponse)
                                                      .toList();

         return ResponseEntity.ok(response);
     }
     
     @GetMapping("/{id}")
     public ResponseEntity<UserResponse> getUserById(@PathVariable Long id){

         User user = userService.getUserById((id));

         UserResponse response = userMapper.toResponse(user);

         return ResponseEntity.ok(response);
     }
     
     @PutMapping("/{id}")
     /* Sample JSON for Postman PUT /api/users/{id}
      {
        "name": "Jane Doe Updated",
        "email": "jane.updated@example.com",
        "role": "MANAGER"
      }
     */
     public ResponseEntity<UserResponse> updateUser(@PathVariable Long id,@Valid @RequestBody UpdateUserRequest request){

         User user = userService.getUserById(id);
         userMapper.updateEntity(request, user);

        User updatedUser = userService.updateUser(id, user);

        UserResponse response = userMapper.toResponse(updatedUser);

        return ResponseEntity.ok(response);
         
     }

      @DeleteMapping("/{id}")
     public ResponseEntity<Void> deleteUser(@PathVariable Long id){
            
        userService.deleteUser(id);
        return ResponseEntity.noContent().build();
     }


     
}
