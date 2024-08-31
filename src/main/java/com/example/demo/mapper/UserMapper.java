package com.example.demo.mapper;

import com.example.demo.dto.UserDTO;
import com.example.demo.entity.User;
import com.example.demo.entity.UserTypes;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

public class UserMapper {

    public static User toEntityForHornyGuy (UserDTO dto) {
        return User.builder()
                .email(dto.getEmail())
                .name(dto.getName().toUpperCase())
                .address(dto.getAddress())
                .userTypes(UserTypes.HORNY_GUY)
                .latitude(dto.getLatitude())
                .createdAt(LocalDate.now())
                .phoneNum(dto.getPhoneNum())
                .longitude(dto.getLongitude())
                .build();
    }

    public static UserDTO toDto (User entity) {
        return UserDTO.builder()
                .id(entity.getId())
                .name(entity.getName())
                .email(entity.getEmail())
                .address(entity.getAddress())
                .userTypes(entity.getUserTypes())
                .address(entity.getAddress())
                .phoneNum(entity.getPhoneNum())
                .longitude(entity.getLongitude())
                .latitude(entity.getLatitude())
                .createdAt(entity.getCreatedAt())
                .referenceCode(entity.getReferenceCode())
                .build();
    }

    public static User toEntityForDateGirl (UserDTO dto) {
        return User.builder()
                .email(dto.getEmail())
                .name(dto.getName().toUpperCase())
                .address(dto.getAddress())
                .userTypes(UserTypes.DATE_GIRL)
                .latitude(dto.getLatitude())
                .phoneNum(dto.getPhoneNum())
                .createdAt(LocalDate.now())
                .longitude(dto.getLongitude())
                .build();
    }

    public static User toEntityForAdmin (UserDTO dto) {
        return User.builder()
                .email(dto.getEmail())
                .name(dto.getName().toUpperCase())
                .address(dto.getAddress())
                .createdAt(LocalDate.now())
                .userTypes(UserTypes.ADMIN)
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