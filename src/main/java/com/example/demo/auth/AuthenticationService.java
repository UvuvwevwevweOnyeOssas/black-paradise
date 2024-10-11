package com.example.demo.auth;

import com.example.demo.common.AUTHResponse;
import com.example.demo.common.Constant;
import com.example.demo.config.IJwtService;
import com.example.demo.dto.UserDTO;
import com.example.demo.entity.Token;
import com.example.demo.entity.TokenType;
import com.example.demo.entity.User;
import com.example.demo.exception.ApplicationErrorException;
import com.example.demo.mapper.UserMapper;
import com.example.demo.repository.TokenRepository;
import com.example.demo.repository.UserRepository;
import com.example.demo.util.DateUtils;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import java.io.IOException;
import java.util.List;
import java.util.Objects;
import java.util.Random;

@Service
@RequiredArgsConstructor
@Transactional
public class AuthenticationService {

    private final UserRepository userRepository;
    private final TokenRepository tokenRepository;
    private final IJwtService iJwtService;
    private final AuthenticationManager authenticationManager;
    private final PasswordEncoder passwordEncoder;

    @Value("${default.admin.code}")
    private String adminCode;

    public AUTHResponse authenticate(AuthenticationRequest request) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getEmail(),
                        request.getPassword()
                )
        );
        var user = userRepository.findByEmail(request.getEmail());
        if (user == null || !user.isEnabled()) {
            return AUTHResponse.fail(Constant.USER_NOT_FOUND, "User is enabled or not found");
        }
        var jwtToken = iJwtService.generateToken(user);
        var refreshToken = iJwtService.generateRefreshToken(user);
        revokeAllUserTokens(user);
        saveUserToken(user, jwtToken);
        return AUTHResponse.success(Constant.AUTH_SUCCESS, AuthenticationResponse.builder()
                .accessToken(jwtToken)
                .refreshToken(refreshToken)
                .build());
    }

    private void saveUserToken(User user, String jwtToken) {
        var token = Token.builder()
                .user(user)
                .token(jwtToken)
                .tokenType(TokenType.BEARER)
                .expired(false)
                .revoked(false)
                .loginAt(DateUtils.getNowDate())
                .build();
        tokenRepository.save(token);
    }

    private void revokeAllUserTokens(User user) {
        var validUserTokens = tokenRepository.findAllValidTokenByUser(user.getId());
        if (validUserTokens.isEmpty())
            return;
        validUserTokens.forEach(token -> {
            token.setExpired(true);
            token.setRevoked(true);
            token.setLogoutAt(DateUtils.getNowDate());
        });
        tokenRepository.saveAll(validUserTokens);
    }

    public void refreshToken(HttpServletRequest request, HttpServletResponse response) throws IOException {
        final String authHeader = request.getHeader(HttpHeaders.AUTHORIZATION);
        final String refreshToken;
        final String userEmail;
        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            return;
        }
        refreshToken = authHeader.substring(7);
        userEmail = iJwtService.extractUsername(refreshToken);
        if (userEmail != null) {
            var user = this.userRepository.findByEmail(userEmail);
            if (iJwtService.isTokenValid(refreshToken, user)) {
                var accessToken = iJwtService.generateToken(user);
                revokeAllUserTokens(user);
                saveUserToken(user, accessToken);
                var authResponse = AuthenticationResponse.builder()
                        .accessToken(accessToken)
                        .refreshToken(refreshToken)
                        .build();
                new ObjectMapper().writeValue(response.getOutputStream(), authResponse);
            }
        }
    }

    public AUTHResponse getAll() {
        List<User> users = userRepository.findAll();
        return ObjectUtils.isEmpty(users)?
                AUTHResponse.fail("Fail!"):
                AUTHResponse.success("Success", users);
    }

    public AUTHResponse register(UserDTO userDTO, int type) {
        if (userRepository.existsByEmailAndPhoneNum(userDTO.getEmail(), userDTO.getPhoneNum())) {
            return AUTHResponse.fail(Constant.USER_REGISTERED);
        }
        User user;
        switch (type) {
            case 1:
                user = UserMapper.toEntityForHornyGuy(userDTO, passwordEncoder);
                user = userRepository.save(user);
                break;
            case 2:
                if (!Objects.equals(adminCode, userDTO.getAdminCode())) {
                    return AUTHResponse.fail(Constant.ADMIN_CODE);
                }
                user = UserMapper.toEntityForDateGirl(userDTO, passwordEncoder);
                String userCode = generateUserCode(userDTO.getName(), userDTO.getAmount());
                user.setReferenceCode(userCode);
                user = userRepository.save(user);
                break;
            case 3:
                user = UserMapper.toEntityForAdmin(userDTO, passwordEncoder);
                user = userRepository.save(user);
                break;
            default:
                return AUTHResponse.fail(Constant.INVALID_USER_TYPE);
        }
        return AUTHResponse.success(Constant.USER_REGISTER_SUCCESS, UserMapper.toDto(user));
    }

    private String generateUserCode(String name, long amount) {
        String[] nameParts = name.split(" ");
        StringBuilder initials = new StringBuilder();

        for (String part : nameParts) {
            if (!part.isEmpty()) {
                initials.append(part.charAt(0));
            }
        }

        String userLevelPrefix = amount >= 1000000 ? "H" : "N";
        Random random = new Random();
        int randomNumber = 100000 + random.nextInt(900000);
        return userLevelPrefix + initials.toString().toUpperCase() + randomNumber;
    }

    public AUTHResponse changePassword(String email, ChangePasswordRequestDTO request) {
        // Fetch the user
        User user = userRepository.findByEmail(email);
        if (user == null) {
            return AUTHResponse.fail(Constant.USER_NOT_FOUND, "User not found");
        }

        // Verify the old password
        if (!passwordEncoder.matches(request.getOldPassword(), user.getPassword())) {
            return AUTHResponse.fail("Old password is incorrect");
        }

        // Update with the new password
        user.setPassword(passwordEncoder.encode(request.getNewPassword()));
        userRepository.save(user);

        return AUTHResponse.success(Constant.USER_PASS_CHANGE_SUCCESS,"User password Change Success");
    }
}
