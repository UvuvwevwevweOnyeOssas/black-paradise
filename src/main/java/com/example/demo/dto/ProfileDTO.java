package com.example.demo.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ProfileDTO {

    private String faceImg;
    private String blurImg;
    private String frontImg;
    private String sideImg;
    private String backImg;
    private int boobSize;
    private int waistSize;
    private int hipSize;
    private double height;
}
