package com.example.backend.service.impl;

import com.example.backend.exception.BadRequestException;
import com.example.backend.exception.DuplicateUserException;
import com.example.backend.exception.NotFoundException;
import com.example.backend.model.entity.AppUser;
import com.example.backend.model.entity.Classroom;
import com.example.backend.model.entity.Generation;
import com.example.backend.model.request.AppUserRequest;
import com.example.backend.model.response.AppUserResponse;
import com.example.backend.repository.AppUserRepository;
import com.example.backend.repository.ClassroomRepository;
import com.example.backend.repository.GenerationRepository;
import com.example.backend.repository.StudentRepository;
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
    private final ClassroomRepository classroomRepository;
    private final GenerationRepository generationRepository;
    private final StudentRepository studentRepository;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        return appUserRepository.getUserByEmail(email);
    }


    @Override
    public AppUserResponse register(AppUserRequest request) {


        AppUser existing = appUserRepository.getUserByEmail(request.getEmail());
        if (existing != null) {
            throw new DuplicateUserException("Email already registered: " + request.getEmail());
        }

        Classroom classroom = classroomRepository.getClassByName(request.getClassName().name());
        if (classroom == null) {
            throw new NotFoundException("Classroom not found: " + request.getClassName());
        }

        Generation generation = generationRepository.getGenByNum(request.getGenNum());
        if (generation == null) {
            throw new NotFoundException("Generation not found: " + request.getGenNum());
        }

        request.setPassword(passwordEncoder.encode(request.getPassword()));

        appUserRepository.insertUser(request, generation.getGenId());
        AppUser appUser = appUserRepository.getUserAfterInsert(request.getEmail());

        if (appUser == null) {
            throw new BadRequestException("Registration failed, please try again.");
        }

        if (request.isStudent()) {
            studentRepository.insertStudent(appUser.getUserId(), classroom.getClassId());
        }

        return modelMapper.map(appUser, AppUserResponse.class);
    }
}
