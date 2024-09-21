package com.example.demo.mapper;

import com.example.demo.dto.UserDTO;
import com.example.demo.entity.User;
import com.example.demo.entity.Role;
import com.example.demo.util.DateUtils;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

public class UserMapper {

    public static User toEntityForHornyGuy (UserDTO dto, PasswordEncoder passwordEncoder) {
        return User.builder()
                .email(dto.getEmail())
                .name(dto.getName().toUpperCase())
                .password(passwordEncoder.encode(dto.getPassword()))
                .address(dto.getAddress())
                .userTypes(Role.HORNY_GUY)
                .latitude(dto.getLatitude())
                .createdAt(DateUtils.stringToLongDate(dto.getCreatedAt()))
                .phoneNum(dto.getPhoneNum())
                .longitude(dto.getLongitude())
                .build();
    }

    public static UserDTO toDto (User entity) {
        return UserDTO.builder()
                .id(entity.getId())
                .name(entity.getName())
                .email(entity.getEmail())
                .password("*****")
                .address(entity.getAddress())
                .userTypes(entity.getUserTypes())
                .address(entity.getAddress())
                .phoneNum(entity.getPhoneNum())
                .longitude(entity.getLongitude())
                .latitude(entity.getLatitude())
                .createdAt(DateUtils.longToStringDate(entity.getCreatedAt()))
                .referenceCode(entity.getReferenceCode())
                .build();
    }

    public static User toEntityForDateGirl (UserDTO dto, PasswordEncoder passwordEncoder) {
        return User.builder()
                .email(dto.getEmail())
                .name(dto.getName().toUpperCase())
                .password(passwordEncoder.encode(dto.getPassword()))
                .address(dto.getAddress())
                .userTypes(Role.DATE_GIRL)
                .latitude(dto.getLatitude())
                .phoneNum(dto.getPhoneNum())
                .createdAt(DateUtils.stringToLongDate(dto.getCreatedAt()))
                .longitude(dto.getLongitude())
                .build();
    }

    public static User toEntityForAdmin (UserDTO dto, PasswordEncoder passwordEncoder) {
        return User.builder()
                .email(dto.getEmail())
                .name(dto.getName().toUpperCase())
                .password(passwordEncoder.encode(dto.getPassword()))
                .address(dto.getAddress())
                .createdAt(DateUtils.stringToLongDate(dto.getCreatedAt()))
                .userTypes(Role.ADMIN)
                .latitude(dto.getLatitude())
                .phoneNum(dto.getPhoneNum())
                .longitude(dto.getLongitude())
                .build();
    }
    public static List<UserDTO> entityToDTOList(final List<User> users) {
        return users.stream()
                .map(UserMapper::toDto)
                .collect(Collectors.toList());

    }
}