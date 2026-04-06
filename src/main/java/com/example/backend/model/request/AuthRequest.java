package com.example.backend.model.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class AuthRequest {
    @NotBlank
    @NotNull
    @Schema(example = "Enter your email")
    private String email;
    @NotNull
    @NotBlank
    @Schema(example = "Qwer123@")
    private String password;
}
