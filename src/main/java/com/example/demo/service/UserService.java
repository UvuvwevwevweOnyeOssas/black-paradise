package com.example.demo.service;

import com.example.demo.dto.UserDTO;
import com.example.demo.entity.User;
import com.example.demo.mapper.UserMapper;
import com.example.demo.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Service
@RequiredArgsConstructor
@Slf4j
public class UserService {

    @Value("${default.admin.code}")
    private String adminCode;


    private final UserRepository userRepository;


    public UserDTO register(UserDTO userDTO, int type) {
        if (userRepository.existsByEmailAndPhoneNum(userDTO.getEmail(), userDTO.getPhoneNum()))
            throw new RuntimeException("User already existed in system");
        User user = new User();
        if (type == 1){
            user = UserMapper.toEntityForHornyGuy(userDTO);
            user = userRepository.save(user);
        }

        if (type == 2) {
            if(!Objects.equals(adminCode, userDTO.getAdminCode()))
                throw new RuntimeException("Code must be equal");

            user = UserMapper.toEntityForDateGirl(userDTO);
            user = userRepository.save(user);
        }

        if (type == 3) {
            user = UserMapper.toEntityForAdmin(userDTO);
            user = userRepository.save(user);
        }

        return UserMapper.toDto(user);
    }
}
