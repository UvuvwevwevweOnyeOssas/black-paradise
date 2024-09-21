package com.example.demo.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "profile")
public class Profile {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
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

    @OneToOne
    @JoinColumn(name = "user_id")
    private User user;

    public void setUser(User user) {
        this.user = user;
        if (user != null && user.getProfile() != this) {
            user.setProfile(this); // Set the profile on the user side
        }
    }


}
