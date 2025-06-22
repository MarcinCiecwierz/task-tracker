package com.example.tasktracker.DTO;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDate;

@Data
@AllArgsConstructor
public class CommentDTO {
    private String comment;
    private LocalDate createdAt;
    private String author;
}
