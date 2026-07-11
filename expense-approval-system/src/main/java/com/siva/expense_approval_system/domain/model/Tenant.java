package com.siva.expense_approval_system.domain.model;

import java.time.LocalDateTime;

import jakarta.persistence.*;

@Entity
@Table(name = "Tenant")
public class Tenant{
        
     @Id
     @GeneratedValue(strategy =  GenerationType.IDENTITY)
     private Long Id;
    
    @Column(nullable = false)
     private String name;
     
     @Column(name = "created_at" ,nullable = false)
     private LocalDateTime created_at;

   
     public Tenant(){

     }
      
     public Tenant(Long Id,String name,LocalDateTime created_at){
         
          this.Id = Id;
          this.name = name;
          this.created_at = created_at;
     }

     public Long Id(){
        return Id;
     }

     public void setId(Long Id){
      this.Id = Id;
     }

     public String name(){
        return name;

     }

     public void setName(String name){
        this.name = name;
     }

     public LocalDateTime created_at(){
        return created_at;
     }

     public void setcreated_At(LocalDateTime created_at){
      this.created_at = created_at;
     }



}