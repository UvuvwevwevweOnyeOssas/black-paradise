package com.example.demo.service;

import com.example.demo.common.AUTHResponse;
import com.example.demo.common.Constant;
import com.example.demo.dto.UserDTO;
import com.example.demo.entity.User;
import com.example.demo.entity.Role;
import com.example.demo.mapper.UserMapper;
import com.example.demo.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Random;

@Service
@RequiredArgsConstructor
@Slf4j
public class UserService {
    @Value("${default.admin.code}")
    private String adminCode;

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public AUTHResponse register(UserDTO userDTO, int type) {
        if (userRepository.existsByEmailAndPhoneNum(userDTO.getEmail(), userDTO.getPhoneNum())) {
            return AUTHResponse.fail(Constant.USER_REGISTERED);
        }
        User user;
        switch (type) {
            case 1:
                user = UserMapper.toEntityForHornyGuy(userDTO, passwordEncoder);
                user = userRepository.save(user);
                break;
            case 2:
                if (!Objects.equals(adminCode, userDTO.getAdminCode())) {
                    return AUTHResponse.fail(Constant.ADMIN_CODE);
                }
                user = UserMapper.toEntityForDateGirl(userDTO, passwordEncoder);
                String userCode = generateUserCode(userDTO.getName(), userDTO.getAmount());
                user.setReferenceCode(userCode);
                user = userRepository.save(user);
                break;
            case 3:
                user = UserMapper.toEntityForAdmin(userDTO, passwordEncoder);
                user = userRepository.save(user);
                break;
            default:
                return AUTHResponse.fail(Constant.INVALID_USER_TYPE);
        }
        return AUTHResponse.success(Constant.USER_REGISTER_SUCCESS, UserMapper.toDto(user));
    }

    private String generateUserCode(String name, long amount) {
        String[] nameParts = name.split(" ");
        StringBuilder initials = new StringBuilder();

        for (String part : nameParts) {
            if (!part.isEmpty()) {
                initials.append(part.charAt(0));
            }
        }

        String userLevelPrefix = amount >= 1000000 ? "H" : "N";
        Random random = new Random();
        int randomNumber = 100000 + random.nextInt(900000);
        return userLevelPrefix + initials.toString().toUpperCase() + randomNumber;
    }

    public List<UserDTO> getAll(int type){
        List<User> users= new ArrayList<>();
        if(type==1)
            users=userRepository.findAllByUserTypes(Role.HORNY_GUY );
        if(type==2)
            users=userRepository.findAllByUserTypes(Role.DATE_GIRL);
        if(type==3)
            users=userRepository.findAllByUserTypes(Role.ADMIN );
        return UserMapper.entityToDTOList(users);
    }
}
