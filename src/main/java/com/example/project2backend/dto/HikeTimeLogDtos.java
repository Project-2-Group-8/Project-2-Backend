package com.example.project2backend.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDateTime;

public class HikeTimeLogDtos {

    // POST /api/hike-times
    public record StartRequest(
            @NotBlank String trailName,
            String notes,
            boolean running
    ) {}

    // PUT /api/hike-times/{id} 
    public record ReplaceRequest(
            @NotBlank String trailName,
            @NotNull LocalDateTime startedAt,
            LocalDateTime endedAt,
            String notes
    ) {}

    // PATCH /api/hike-times/{id} 
    public record PatchRequest(
            String trailName,
            LocalDateTime startedAt,
            LocalDateTime endedAt,
            String notes
    ) {}
}