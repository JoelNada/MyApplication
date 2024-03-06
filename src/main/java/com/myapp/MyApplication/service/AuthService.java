package com.myapp.MyApplication.service;

import com.myapp.MyApplication.models.dto.AuthRequestDTO;
import com.myapp.MyApplication.models.dto.RegisterDTO;

public interface AuthService {
    String login(AuthRequestDTO authRequestDTO);
    String register(RegisterDTO registerDTO);
}
