package com.taxi.taxihailcore.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "tokens")
public class Token {

    @Id
    @Column(name = "token_id", columnDefinition = "UUID default gen_random_uuid()")
    @GeneratedValue(strategy = GenerationType.AUTO)
    public UUID tokeId;

    @Column(name = "token", unique = true, nullable = false)
    public String token;

    @Enumerated(EnumType.STRING)
    @Column(name = "token_type", nullable = false)
    public TokenType tokenType;

    @Column(name = "is_revoked", nullable = false)
    public boolean isRevoked;

    @Column(name = "is_expired", nullable = false)
    public boolean isExpired;

    @ManyToOne(optional = false)
    @JoinColumn(name = "auth_user", nullable = false)
    public User authUser;

}
