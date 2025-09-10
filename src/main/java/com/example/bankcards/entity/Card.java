package com.example.bankcards.entity;

//import com.example.bankcards.entity.User;

import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Getter
@Setter
//@NoArgsConstructor
//@AllArgsConstructor
//@Builder
@Table(name = "cards")
public class Card {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "card_number", unique = true, nullable = false)
    private String cardNumber;

    @Column(name = "masked_number", nullable = false)
    private String maskedNumber;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "owner_id", nullable = false)
    private User owner;

    @Column(name = "expiry_date", nullable = false)
    private LocalDate expiryDate;

    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false)
    private CardStatus status;

    @Column(name = "balance", nullable = false, precision = 15, scale = 2)
    private BigDecimal balance = BigDecimal.ZERO;

    @Column(name = "card_type")
    private String cardType = "DEBIT";

    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    @Column(name = "blocked_at")
    private LocalDateTime blockedAt;

    @Column(name = "block_reason")
    private String blockReason;

    public Card(){}

    public Card(String cardNumber, String maskedNumber, User owner, LocalDate expiryDate, CardStatus status) {
        this.cardNumber = cardNumber;
        this.maskedNumber = maskedNumber;
        this.owner = owner;
        this.expiryDate = expiryDate;
        this.status = status;
        this.createdAt = LocalDateTime.now();
        this.updatedAt = LocalDateTime.now();
    }

    @PrePersist
    protected void onCreate(){
        createdAt = LocalDateTime.now();
        updatedAt = LocalDateTime.now();
    }

    @PreUpdate
    protected void onUpdate(){
        updatedAt = LocalDateTime.now();
    }

    public boolean isExpired(){
        return expiryDate.isBefore(LocalDate.now());
    }

    public void block(String reason){
        this.status = CardStatus.BLOCKED;
        this.blockReason = reason;
        this.blockedAt = LocalDateTime.now();
    }

    public void activate(){
        this.status = CardStatus.ACTIVE;
        this. blockReason = null;
        this.blockedAt = null;
    }

    public boolean isOperational(){
        return status == CardStatus.ACTIVE && !isExpired();
    }
}
