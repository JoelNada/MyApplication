package com.myapp.MyApplication.controller;


import com.myapp.MyApplication.models.dto.AuthRequestDTO;
import com.myapp.MyApplication.models.dto.RegisterDTO;
import com.myapp.MyApplication.repository.UserRepository;
import com.myapp.MyApplication.service.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/auth/")
public class AuthController {
    @Autowired
    private AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody RegisterDTO registerDTO){
       String message = authService.register(registerDTO);
       return ResponseEntity.ok(message);
   }

   @PostMapping("/login")
   public ResponseEntity<?> login(@RequestBody AuthRequestDTO authRequestDTO) throws BadCredentialsException {
        String message = authService.login(authRequestDTO);
        return ResponseEntity.ok(message);
   }
}
