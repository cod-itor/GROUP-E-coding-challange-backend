package com.example.backend.service.impl;

import com.example.backend.model.entity.AppUser;
import com.example.backend.model.request.AppUserRequest;
import com.example.backend.model.response.AppUserResponse;
import com.example.backend.repository.AppUserRepository;
import com.example.backend.service.AppUserService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;


@Service
@RequiredArgsConstructor
public class AppUserServiceImpl implements AppUserService {
    private final AppUserRepository appUserRepository;
    private final PasswordEncoder passwordEncoder;
    private final ModelMapper modelMapper;

    @Override
    public UserDetails loadUserByUsername(String identifier) throws UsernameNotFoundException {
        AppUser user = appUserRepository.getUserByEmailOrUsername(identifier);
        if (user == null) throw new UsernameNotFoundException("User not found");
        System.out.println("User logging in: " + identifier);
        System.out.println("Authorities found: " + user.getAuthorities());
        return user;
    }

//    @Override
//    public AppUserResponse register(AppUserRequest request) {
//        request.setPassword(passwordEncoder.encode(request.getPassword()));
//        AppUser appUser = appUserRepository.register(request);
//        return modelMapper.map(appUserRepository.getUserById(appUser.getUserId()), AppUserResponse.class);
//    }
}
