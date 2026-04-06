package com.example.backend.controller;

import com.example.backend.exception.BadRequestException;
import com.example.backend.jwt.JwtService;
import com.example.backend.model.entity.AppUser;
import com.example.backend.model.request.AppUserRequest;
import com.example.backend.model.request.AuthRequest;
import com.example.backend.model.response.ApiResponse;
import com.example.backend.model.response.AppUserResponse;
import com.example.backend.model.response.AuthResponse;
import com.example.backend.service.AppUserService;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import tools.jackson.databind.json.JsonMapper;

import java.time.Instant;

@RestController
@RequestMapping("/api/v1/auths")
@RequiredArgsConstructor
public class AuthController {
    private final AppUserService appUserService;
    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;

    private void authenticate(String email, String password) throws Exception {
        try {
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(email, password)
            );
        } catch (DisabledException e) {
            throw new BadRequestException("Account is disabled.");
        } catch (BadCredentialsException e) {
            throw new BadRequestException("Invalid username, email, or password. Please check your credentials and try again.");
        }
    }


    @Operation(summary = "User Login")
    @PostMapping("/login")
    public ResponseEntity<ApiResponse<AuthResponse>> authenticate(@RequestBody @Valid AuthRequest request) throws Exception{

        authenticate(request.getEmail(), request.getPassword());
        final UserDetails userDetails = appUserService.loadUserByUsername(request.getEmail());

        AppUser user = (AppUser) userDetails;


        final String token = jwtService.generateToken(userDetails);
        AuthResponse authResponse = new AuthResponse(token);
        ApiResponse<AuthResponse> apiResponse = ApiResponse.<AuthResponse>builder()
                .success(true)
                .message("Login successful! Authentication token generated.")
                .status(HttpStatus.OK.name())
                .payload(authResponse)
                .timestamp(Instant.now())
                .build();
        return new ResponseEntity<>(apiResponse, HttpStatus.OK);
    }

//    @Operation(summary = "Register a new user")
//    @PostMapping("/register")
//    public ResponseEntity<ApiResponse<AppUserResponse>> register(@RequestBody @Valid AppUserRequest request){
//        AppUserResponse appUserResponse = appUserService.register(request);
//
//        ApiResponse<AppUserResponse> apiResponse = ApiResponse.<AppUserResponse>builder()
//                .success(true)
//                .message("User registered successfully! Please verify your email to complete the registration.")
//                .status(HttpStatus.CREATED.name())
//                .payload(appUserResponse)
//                .timestamp(Instant.now())
//                .build();
//        return new ResponseEntity<>(apiResponse, HttpStatus.CREATED);
//    }
}
