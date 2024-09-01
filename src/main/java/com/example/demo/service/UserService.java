package com.example.demo.service;

import com.example.demo.common.AUTHResponse;
import com.example.demo.common.Constant;
import com.example.demo.dto.UserDTO;
import com.example.demo.entity.User;
import com.example.demo.entity.UserTypes;
import com.example.demo.mapper.UserMapper;
import com.example.demo.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Service
@RequiredArgsConstructor
@Slf4j
public class UserService {
    @Value("${default.admin.code}")
    private String adminCode;

    private final UserRepository userRepository;

    public AUTHResponse register(UserDTO userDTO, int type) {
        if (userRepository.existsByEmailAndPhoneNum(userDTO.getEmail(), userDTO.getPhoneNum())) {
            return AUTHResponse.fail(Constant.USER_REGISTERED);
        }
        User user = new User();
        if (type == 1){
            user = UserMapper.toEntityForHornyGuy(userDTO);
            user = userRepository.save(user);
        }
        if (type == 2) {
            if(!Objects.equals(adminCode, userDTO.getAdminCode())) {
                return AUTHResponse.fail(Constant.ADMIN_CODE);
            }
            user = UserMapper.toEntityForDateGirl(userDTO);
            user = userRepository.save(user);
        }
        if (type == 3) {
            user = UserMapper.toEntityForAdmin(userDTO);
            user = userRepository.save(user);
        }
        return AUTHResponse.success(Constant.USER_REGISTER_SUCCESS, UserMapper.toDto(user));
    }

    public List<UserDTO> getAll(int type){
        List<User> users= new ArrayList<>();
        if(type==1)
            users=userRepository.findAllByUserTypes(UserTypes.HORNY_GUY );
        if(type==2)
            users=userRepository.findAllByUserTypes(UserTypes.DATE_GIRL);
        if(type==3)
            users=userRepository.findAllByUserTypes(UserTypes.ADMIN );
        return UserMapper.entityToDTOList(users);
    }
}
