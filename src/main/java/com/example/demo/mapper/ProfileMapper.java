package com.example.demo.mapper;

import com.example.demo.dto.ProfileDTO;
import com.example.demo.entity.Profile;

public class ProfileMapper {

    public static Profile dtoToEntity( ProfileDTO profileDTO){

        return Profile.builder()
                .id(profileDTO.getId())
                .backImg(profileDTO.getBackImg())
                .blurImg(profileDTO.getBlurImg())
                .boobSize(profileDTO.getBoobSize())
                .faceImg(profileDTO.getFaceImg())
                .frontImg(profileDTO.getFrontImg())
                .height(profileDTO.getHeight())
                .hipSize(profileDTO.getHipSize())
                .sideImg(profileDTO.getSideImg())
                .waistSize(profileDTO.getWaistSize())
                .build();
    }
    public static ProfileDTO entityToDTO( Profile profile){

        return ProfileDTO.builder()
                .id(profile.getId())
                .backImg(profile.getBackImg())
                .blurImg(profile.getBlurImg())
                .boobSize(profile.getBoobSize())
                .faceImg(profile.getFaceImg())
                .frontImg(profile.getFrontImg())
                .height(profile.getHeight())
                .hipSize(profile.getHipSize())
                .sideImg(profile.getSideImg())
                .waistSize(profile.getWaistSize())
                .userDTO(UserMapper.toDto(profile.getUser()))
                .build();
    }

}
