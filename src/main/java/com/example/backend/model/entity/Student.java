package com.example.backend.model.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.jspecify.annotations.NullMarked;

import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Student {
    private UUID stuId;
    private UUID userId;
    private UUID classId;
}
