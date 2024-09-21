package com.example.demo.entity;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Collection;
import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "user")
public class User implements UserDetails, Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    @Enumerated(EnumType.STRING)
    private Role userTypes;
    private String phoneNum;
    private String email;
    private String password;
    private String address;
    private BigDecimal latitude;
    private BigDecimal longitude;
    private String referenceCode;
    private Long createdAt;

    @OneToOne(mappedBy = "user")
    private Profile profile;

    public void setProfile(Profile profile) {
        this.profile = profile;
        if (profile != null && profile.getUser() != this) {
            profile.setUser(this); // Set the user on the profile side
        }
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return userTypes.getAuthorities();
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return email;
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
