package com.example.demo.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ProfileDTO {

    private Long id;
    private String faceImg;
    private String blurImg;
    private String frontImg;
    private String sideImg;
    private String backImg;
    private int boobSize;
    private int waistSize;
    private int hipSize;
    private double height;
    private Long userId;
    private UserDTO userDTO;
    private MultipartFile faceImage;
    private MultipartFile blurImage;
    private MultipartFile frontImage;
    private MultipartFile sideImage;
    private MultipartFile backImage;



}
