package com.example.demo.service;


import com.example.demo.dto.ProfileDTO;
import com.example.demo.dto.UserDTO;
import com.example.demo.entity.Profile;
import com.example.demo.entity.User;
import com.example.demo.exception.ApplicationErrorException;
import com.example.demo.mapper.ProfileMapper;
import com.example.demo.mapper.UserMapper;
import com.example.demo.repository.ProfileRepository;
import com.example.demo.repository.UserRepository;
import com.example.demo.util.FileUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import java.io.IOException;

@Service
@RequiredArgsConstructor
@Slf4j
public class ProfileService {

    @Value("${girl.file.path.backImage.absolutePath}")
    private String girlBackAbsolutePath;

    @Value("${girl.file.path.backImage.relativePath}")
    private String girlBackRelativePath;

    @Value("${girl.file.path.faceImage.absolutePath}")
    private String girlFaceAbsolutePath;

    @Value("${girl.file.path.faceImage.relativePath}")
    private String girlFaceRelativePath;

    @Value("${girl.file.path.sideImage.absolutePath}")
    private String girlSideAbsolutePath;

    @Value("${girl.file.path.sideImage.relativePath}")
    private String girlSideRelativePath;

    @Value("${girl.file.path.blurImage.absolutePath}")
    private String girlBlurAbsolutePath;

    @Value("${girl.file.path.blurImage.relativePath}")
    private String girlBlurRelativePath;

    @Value("${girl.file.path.frontImage.absolutePath}")
    private String girlFrontAbsolutePath;

    @Value("${girl.file.path.frontImage.relativePath}")
    private String girlFrontRelativePath;

    private final ProfileRepository profileRepository;
    private final UserRepository userRepository;
    private final FileUtil fileUtil;

    public ProfileDTO saveProfile(ProfileDTO profileDTO) {


        String imagePath = null;

        if (profileDTO.getBackImage() != null && !profileDTO.getBackImage().isEmpty()) {
            try {
                imagePath = fileUtil.writeMediaFile(profileDTO.getBackImage(), girlBackAbsolutePath, girlBackRelativePath);
                profileDTO.setBackImg(imagePath);
            } catch (IOException e) {
                throw new ApplicationErrorException("Error saving image file", e);
            }
        }
        if (profileDTO.getBlurImg() != null && !profileDTO.getBlurImg().isEmpty()) {
            try {
                imagePath = fileUtil.writeMediaFile(profileDTO.getBlurImage(), girlBlurAbsolutePath, girlBlurRelativePath);
                profileDTO.setBlurImg(imagePath);
            } catch (IOException e) {
                throw new ApplicationErrorException("Error saving image file", e);
            }
        }
        if (profileDTO.getFaceImage() != null && !profileDTO.getFaceImage().isEmpty()) {
            try {
                imagePath = fileUtil.writeMediaFile(profileDTO.getFaceImage(), girlFaceAbsolutePath, girlFaceRelativePath);
                profileDTO.setFaceImg(imagePath);
            } catch (IOException e) {
                throw new ApplicationErrorException("Error saving image file", e);
            }
        }
        if (profileDTO.getFrontImage() != null && !profileDTO.getFrontImage().isEmpty()) {
            try {
                imagePath = fileUtil.writeMediaFile(profileDTO.getFrontImage(), girlFrontAbsolutePath, girlFrontRelativePath);
                profileDTO.setFrontImg(imagePath);
            } catch (IOException e) {
                throw new ApplicationErrorException("Error saving image file", e);
            }
        }
        if (profileDTO.getSideImage() != null && !profileDTO.getSideImage().isEmpty()) {
            try {
                imagePath = fileUtil.writeMediaFile(profileDTO.getSideImage(), girlSideAbsolutePath, girlSideRelativePath);
                profileDTO.setSideImg(imagePath);
            } catch (IOException e) {
                throw new ApplicationErrorException("Error saving image file", e);
            }
        }

        final Profile profile= profileRepository.save(ProfileMapper.dtoToEntity(profileDTO));

        User user = userRepository.findById(profileDTO.getUserId()).orElseThrow();
        user.setProfile(profile);
        userRepository.save(user);
        UserDTO userDTO = UserMapper.toDto(user);

        ProfileDTO profileDTO1 = ProfileMapper.entityToDTO(profile);
        profileDTO1.setUserDTO(userDTO);

        return profileDTO1;
    }
}
