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
@Table(name = "Refresh_token")
public class RefreshToken {
    

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)

    private Long Id;
   
    @ManyToOne
    @JoinColumn(name ="user_id",nullable = false)
    private User user;
    
    @Column(name = "token_hash",nullable = false,length = 500)
    private String tokenHash;
    
    @Column(name = "expires_at",nullable = false)
    private LocalDateTime expiredAt;
    
    @Column(nullable = false)
    private Boolean revoked;
    
    @Column(name = "created_at",nullable = false)
    private LocalDateTime created_At;


    public RefreshToken(){

    }


    public RefreshToken(Long id, User user, String tokenHash, LocalDateTime expiredAt, Boolean revoked,
            LocalDateTime created_At) {
        Id = id;
        this.user = user;
        this.tokenHash = tokenHash;
        this.expiredAt = expiredAt;
        this.revoked = revoked;
        this.created_At = created_At;
    }


    public Long getId() {
        return Id;
    }


    public void setId(Long id) {
        Id = id;
    }


    public User getUser() {
        return user;
    }


    public void setUser(User user) {
        this.user = user;
    }


    public String getTokenHash() {
        return tokenHash;
    }


    public void setTokenHash(String tokenHash) {
        this.tokenHash = tokenHash;
    }


    public LocalDateTime getExpiredAt() {
        return expiredAt;
    }


    public void setExpiredAt(LocalDateTime expiredAt) {
        this.expiredAt = expiredAt;
    }


    public Boolean getRevoked() {
        return revoked;
    }


    public void setRevoked(Boolean revoked) {
        this.revoked = revoked;
    }


    public LocalDateTime getCreated_At() {
        return created_At;
    }


    public void setCreated_At(LocalDateTime created_At) {
        this.created_At = created_At;
    }

    

}
