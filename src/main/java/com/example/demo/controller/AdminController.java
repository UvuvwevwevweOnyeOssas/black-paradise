package com.example.demo.controller;

import com.example.demo.common.AUTHResponse;
import com.example.demo.dto.ProfileDTO;
import com.example.demo.dto.UserDTO;
import com.example.demo.service.ProfileService;
import com.example.demo.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/admin")
public class AdminController {
    private final UserService userService;
    private final ProfileService profileService;

    @PostMapping
    public ResponseEntity<AUTHResponse> register(@RequestBody UserDTO userDTO) {
        int type = 3;
        return ResponseEntity.ok(userService.register(userDTO, type));
    }

    @GetMapping("find-all")
    public ResponseEntity <List<UserDTO>> findAll(){
        int type = 3;
        return ResponseEntity.ok(userService.getAll(type));
    }

    @PostMapping(value="/save",consumes = { "multipart/form-data" })
    public ResponseEntity<?> saveProfile(@ModelAttribute ProfileDTO profileDTO) {
        return ResponseEntity.ok(profileService.saveProfile(profileDTO));
    }



}
