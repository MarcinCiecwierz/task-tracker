package com.example.tasktracker.DTO;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class RequestNewCommentDTO {
    private String text;
    private String taskId;
}
