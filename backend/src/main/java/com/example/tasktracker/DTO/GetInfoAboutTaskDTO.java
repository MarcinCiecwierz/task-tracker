package com.example.tasktracker.DTO;

import com.example.tasktracker.model.Comment;
import com.example.tasktracker.model.Users;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class GetInfoAboutTaskDTO {
    private String title;
    private String description;
    private List<String> users;
    private List<CommentDTO> comments;
}
