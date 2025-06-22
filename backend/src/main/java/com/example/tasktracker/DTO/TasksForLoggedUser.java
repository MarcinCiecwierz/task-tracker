package com.example.tasktracker.DTO;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class TasksForLoggedUser {
    private String id;
    private String title;
    private String status;
}
