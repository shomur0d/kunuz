package com.company.Lesson_62.controller;

import com.company.Lesson_62.dto.ProfileDTO;
import com.company.Lesson_62.dto.auth.AuthorizationDTO;
import com.company.Lesson_62.dto.auth.RegistrationDTO;
import com.company.Lesson_62.service.AuthService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/auth")
@Api(tags = "Auth")
public class AuthController {
    @Autowired
    private AuthService authService;

    @PostMapping("/login")
    @ApiOperation(value = "Login method", notes = "Sekinroq", nickname = "nickName")
    public ResponseEntity<ProfileDTO> login(@RequestBody AuthorizationDTO dto) {
        ProfileDTO response = authService.authorization(dto);
        return ResponseEntity.ok(response);
    }

    @PostMapping("/registration")
    @ApiOperation(value = "Registration method")
    public ResponseEntity<ProfileDTO> registration(@Valid @RequestBody RegistrationDTO dto) {
        authService.registration(dto);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/verification/{jwt}")
    @ApiOperation(value = "Register method")
    public ResponseEntity<?> verification(@PathVariable("jwt") String jwt) {
        authService.verification(jwt);
        return ResponseEntity.ok().build();
    }




}
