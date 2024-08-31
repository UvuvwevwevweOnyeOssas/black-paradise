package com.example.demo.entity;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "user")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    @Enumerated(EnumType.STRING)
    private UserTypes userTypes;
    private String phoneNum;
    private String email;
    private String address;
    private BigDecimal latitude;
    private BigDecimal longitude;
    private String referenceCode;
    private LocalDate createdAt;

    @OneToOne(mappedBy = "user")
    private Profile profile;


}
