package com.myapp.MyApplication.controller;

import com.myapp.MyApplication.models.entity.User;
import com.myapp.MyApplication.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/user")
public class TestController {
    @Autowired
    UserRepository userRepository;
    @GetMapping("/getusers")
    public ResponseEntity<?> getUser(@AuthenticationPrincipal UserDetails userDetails){
        User user = userRepository.findByUsername(userDetails.getUsername());
        return ResponseEntity.ok().body(user);
    }
}
