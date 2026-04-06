package com.example.backend.model.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AppUserResponse {
    private UUID userId;
    @JsonProperty("username")
    private String fullName;
    private String email;
    private String phoneNumber;
    private boolean isStudent;
    private Long genNum;
    private Instant createdAt;
    private Instant updatedAt;
}
