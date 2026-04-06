package com.example.backend.model.request;

import com.example.backend.model.entity.Classroom;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AppUserRequest {

    @NotBlank
    private String fullName;

    @NotBlank
    @Email
    private String email;

    @NotBlank
    private String password;

    private String phoneNumber;
    @JsonProperty("isStudent")
    @Getter(onMethod_ = @JsonProperty("isStudent"))
    private boolean isStudent;

    @NotNull
    private ClassName className;

    @NotNull
    private Long genNum;
}
