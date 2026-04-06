package com.example.backend.utils;

import com.example.backend.model.entity.AppUser;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
public class HandleCurrentUser {
    public UUID getUserIdOfCurrentUser() {
        AppUser appUser = (AppUser) SecurityContextHolder.getContext().getAuthentication()
                .getPrincipal();
        UUID userId = appUser.getUserId();
        System.out.println(userId);
        return userId;
    }

    public String getUserByEmail() {
        AppUser appUser = (AppUser) SecurityContextHolder.getContext().getAuthentication()
                .getPrincipal();
        String email = appUser.getEmail();
        System.out.println(email);
        return email;
    }
}
