package com.example.demo.dto;

import com.example.demo.entity.UserTypes;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class UserDTO {

    private Long id;
    private String name;
    private UserTypes userTypes;
    private String phoneNum;
    private String email;
    private String address;
    private BigDecimal latitude;
    private BigDecimal longitude;
    private String referenceCode;
    private String adminCode;
    private LocalDate createdAt;
}
