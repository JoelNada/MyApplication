package com.myapp.MyApplication.service;

import com.myapp.MyApplication.models.dto.AuthRequestDTO;
import com.myapp.MyApplication.models.dto.AuthResponseDTO;
import com.myapp.MyApplication.models.dto.RegisterDTO;

public interface AuthService {
    AuthResponseDTO login(AuthRequestDTO authRequestDTO);
    String register(RegisterDTO registerDTO);
}
