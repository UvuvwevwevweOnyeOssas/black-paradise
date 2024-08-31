package com.example.demo.controller;

import com.example.demo.dto.UserDTO;
import com.example.demo.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/horny-guy")
public class HornyGuyController {

    private final UserService userService;

    @PostMapping
    public ResponseEntity<?> register(@RequestBody UserDTO userDTO) {
        int type = 1;
        return ResponseEntity.ok(userService.register(userDTO, type));
    }

    @GetMapping("/findAll")
    public ResponseEntity <List<UserDTO>> findAll(){
        int type = 1;
        return ResponseEntity.ok(userService.getAll(type));
    }

}
