package com.test.sjna.controller;

import com.test.sjna.security.JwtToken;
import com.test.sjna.service.AuthenticationService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/api/auth")
public class AuthController {

    private final AuthenticationService authenticationService;

    @PostMapping(value = "/login")
    public ResponseEntity<JwtToken> login(@RequestBody Map<String, String> loginForm) {
        String userId = loginForm.get("userId");
        String userPw = loginForm.get("userPw");
        JwtToken token = authenticationService.login(userId, userPw);
        return ResponseEntity.ok(token);
    }

}
