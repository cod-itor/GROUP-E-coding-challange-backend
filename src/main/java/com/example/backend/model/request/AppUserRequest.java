package com.example.backend.model.request;

import com.example.backend.model.entity.Classroom;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AppUserRequest {

    private String fullName;

    private Classroom classId;

    private String email;

    private String password;

    private boolean isStudent;
}
