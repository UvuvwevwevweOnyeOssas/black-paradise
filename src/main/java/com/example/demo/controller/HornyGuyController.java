package com.example.demo.controller;

import com.example.demo.common.AUTHResponse;
import com.example.demo.dto.UserDTO;
import com.example.demo.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/horny-guy")
//@PreAuthorize("hasAnyRole('ADMIN', 'HORNY_GUY')")
public class HornyGuyController {
    private final UserService userService;

    @PostMapping("/register")
    public ResponseEntity<AUTHResponse> register(@RequestBody UserDTO userDTO) {
        int type = 1;
        return ResponseEntity.ok(userService.register(userDTO, type));
    }

    @PreAuthorize("hasAnyAuthority('admin:read', 'hornyGuy:read')")
    @GetMapping("find-all")
    public ResponseEntity <List<UserDTO>> findAll(){
        int type = 1;
        return ResponseEntity.ok(userService.getAll(type));
    }

}
