package com.taxi.taxihailcore.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.sql.Timestamp;
import java.util.Collection;
import java.util.List;
import java.util.UUID;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "users")
public class User implements UserDetails {
    @Id
    @Column(name = "user_id", columnDefinition = "UUID default gen_random_uuid()")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID userId;

    @Email(message = "Enter valid Email !")
    @Column(name = "email", length = 64, unique = true, nullable = false, updatable = false)
    private String email;

    @JsonIgnore
    @Column(name = "password", length = 256, nullable = false)
    private String password;

    @Enumerated(EnumType.STRING)
    @Column(name = "role", length = 12, nullable = false, updatable = false)
    private Role role;

    @Column(name = "username", length = 32, unique = true, nullable = false, updatable = false)
    private String userName;

    @Column(name = "mobile", length = 9, unique = true, nullable = false)
    @Size(min = 9, max = 9, message = "Enter valid mobile number !")
    private String mobile;

    @Column(name = "first_name", length = 64, nullable = false)
    private String firstName;

    @Column(name = "last_name", length = 64)
    private String lastName;

    @Column(name = "status", length = 1, nullable = false)
    private int status;

    @Column(name = "last_login")
    private Timestamp lastLogin;

    @CreationTimestamp
    @Column(name = "created_at", updatable = false, nullable = false)
    private Timestamp createdAt;

    @UpdateTimestamp
    @Column(name = "updated_at", nullable = false)
    private Timestamp updatedAt;

    @OneToOne(mappedBy = "driver", cascade = CascadeType.ALL)
    @PrimaryKeyJoinColumn
    private Vehicle vehicle;

    @OneToMany(mappedBy = "passenger", cascade = CascadeType.ALL)
    private List<Ride> passengerRides;

    @OneToMany(mappedBy = "driver", cascade = CascadeType.ALL)
    private List<Ride> driverRides;

    @OneToMany(mappedBy = "driver", cascade = CascadeType.ALL)
    private List<RideDriver> rideDrivers;

    @OneToMany(mappedBy = "authUser")
    private List<Token> tokens;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority(role.name()));
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return userName;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
