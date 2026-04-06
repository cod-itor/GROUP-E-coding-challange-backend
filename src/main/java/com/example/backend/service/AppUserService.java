package com.example.backend.service;

import com.example.backend.model.request.AppUserRequest;
import com.example.backend.model.response.AppUserResponse;
import jakarta.validation.Valid;
import org.springframework.security.core.userdetails.UserDetailsService;

public interface AppUserService extends UserDetailsService {
//    AppUserResponse register(@Valid AppUserRequest request);
}
