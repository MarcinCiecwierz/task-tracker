package com.example.tasktracker.DTO;

import jakarta.annotation.Nullable;
import lombok.Data;

import java.util.List;

@Data
public class TaskCreateRequest {
    private String title;
    @Nullable
    private String description;
    private List<String> usersIds;
}
