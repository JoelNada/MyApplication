package com.myapp.MyApplication.service.serviceImpl;

import com.myapp.MyApplication.models.dto.AuthRequestDTO;
import com.myapp.MyApplication.models.dto.AuthResponseDTO;
import com.myapp.MyApplication.models.dto.RegisterDTO;
import com.myapp.MyApplication.models.entity.User;
import com.myapp.MyApplication.repository.UserRepository;
import com.myapp.MyApplication.security.JwtTokenProvider;
import com.myapp.MyApplication.service.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthServiceImpl implements AuthService {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private JwtTokenProvider jwtTokenProvider;

    @Override
    public AuthResponseDTO login(AuthRequestDTO authRequestDTO) {
        try {
            Authentication authentication = authenticationManager
                    .authenticate(new UsernamePasswordAuthenticationToken(authRequestDTO.getUsername(),
                    authRequestDTO.getPassword()));
            SecurityContextHolder.getContext().setAuthentication(authentication);
            String token = jwtTokenProvider.generateToken(authentication);
            AuthResponseDTO authResponseDTO = new AuthResponseDTO();
            authResponseDTO.setToken(token);
            authResponseDTO.setUsername(authRequestDTO.getUsername());
            return authResponseDTO;
        }
        catch (BadCredentialsException e){
            throw new BadCredentialsException("UnAuthorized !!");
        }

    }

    @Override
    public String register(RegisterDTO registerDTO) {
        User user = new User();
        user.setUsername(registerDTO.getUsername());
        user.setEmail(registerDTO.getEmail());
        user.setFirstName(registerDTO.getFirstName());
        user.setLastName(registerDTO.getLastName());
        user.setPassword(passwordEncoder.encode(registerDTO.getPassword()));
        userRepository.save(user);
        return "Registered Successfully";
    }
}
