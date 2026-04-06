package com.example.backend.service.impl;

import com.example.backend.exception.DuplicateUserException;
import com.example.backend.model.entity.AppUser;
import com.example.backend.model.request.AppUserRequest;
import com.example.backend.model.response.AppUserResponse;
import com.example.backend.repository.AppUserRepository;
import com.example.backend.service.AppUserService;
import lombok.RequiredArgsConstructor;
import org.jspecify.annotations.NullMarked;
import org.modelmapper.ModelMapper;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;


@Service
@NullMarked
@RequiredArgsConstructor
public class AppUserServiceImpl implements AppUserService {
    private final AppUserRepository appUserRepository;
    private final PasswordEncoder passwordEncoder;
    private final ModelMapper modelMapper;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        return appUserRepository.getUserByEmail(email);
    }


//    @Override
//    public AppUserResponse register(AppUserRequest request) {
//
//        if (appUserRepository.existsUserName(request.getUserName())) {
//            throw new DuplicateUserException("The username is already associated with an existing account. Please try with a different one.");
//        }
//
//        if (appUserRepository.existsUserEmail(request.getEmail())){
//            throw new DuplicateUserException("The email is already associated with an existing account. Please try with a different one.");
//        }
//
//        request.setPassword(passwordEncoder.encode(request.getPassword()));
//        AppUser appUser = appUserRepository.register(request);
//        return modelMapper.map(appUserRepository.getUserById(appUser.getAppUserId()), AppUserResponse.class);
//    }


//    @Override
//    public AppUserResponse register(AppUserRequest request) {
//        request.setPassword(passwordEncoder.encode(request.getPassword()));
//        AppUser appUser = appUserRepository.register(request);
//        return modelMapper.map(appUserRepository.getUserById(appUser.getUserId()), AppUserResponse.class);
//    }
}
