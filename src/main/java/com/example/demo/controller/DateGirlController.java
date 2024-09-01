package com.example.demo.controller;

import com.example.demo.common.AUTHResponse;
import com.example.demo.dto.UserDTO;
import com.example.demo.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/date-girl")
public class DateGirlController {
    private final UserService userService;

    @PostMapping
    public ResponseEntity<AUTHResponse> register(@RequestBody UserDTO userDTO) {
        int type = 2;
        return ResponseEntity.ok(userService.register(userDTO, type));
    }

    @GetMapping("find-all")
    public ResponseEntity <List<UserDTO>> findAll(){
        int type = 2;
        return ResponseEntity.ok(userService.getAll(type));
    }
}
