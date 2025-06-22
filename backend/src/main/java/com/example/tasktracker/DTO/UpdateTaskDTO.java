package com.example.tasktracker.DTO;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class UpdateTaskDTO {
    private String taskId;
    private String status;
}
