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
@RequestMapping("/api/v1/date-girl")
//@PreAuthorize("hasAnyRole('ADMIN', 'DATE_GIRL', 'HORNY_GUY')")
public class DateGirlController {
    private final UserService userService;

    @PostMapping("/register")
    public ResponseEntity<AUTHResponse> register(@RequestBody UserDTO userDTO) {
        int type = 2;
        return ResponseEntity.ok(userService.register(userDTO, type));
    }

    @PreAuthorize("hasAnyAuthority('admin:read', 'dateGirl:read', 'hornyGuy:read')")
    @GetMapping("find-all")
    public ResponseEntity <List<UserDTO>> findAll(){
        int type = 2;
        return ResponseEntity.ok(userService.getAll(type));
    }
}
