package com.example.tasktracker.controllers;

import com.example.tasktracker.DTO.RequestNewCommentDTO;
import com.example.tasktracker.appliaction.comment.AddNewCommentToTask;
import com.example.tasktracker.model.Comment;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/comment")
public class CommentController {

    private AddNewCommentToTask addNewCommentToTask;

    public CommentController(AddNewCommentToTask addNewCommentToTask) {
        this.addNewCommentToTask = addNewCommentToTask;
    }

    @PostMapping
    public ResponseEntity<?> postComment(@RequestBody RequestNewCommentDTO commentDTO,
                                         @AuthenticationPrincipal Jwt jwt) {
        System.out.println(commentDTO.getTaskId());
        String sub = jwt.getClaim("sub");
        addNewCommentToTask.addComment(sub, commentDTO);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }
}
