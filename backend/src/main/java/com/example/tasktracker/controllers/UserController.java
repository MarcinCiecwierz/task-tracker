package com.example.tasktracker.controllers;

import com.example.tasktracker.appliaction.user.GetAllUsersMembers;
import com.example.tasktracker.model.Users;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/user")
public class UserController {

    private GetAllUsersMembers getAllUsersMembers;

    public UserController(GetAllUsersMembers getAllUsersMembers) {
        this.getAllUsersMembers = getAllUsersMembers;
    }

    @GetMapping("/list")
    public ResponseEntity<?> getAllUsers() {
        return ResponseEntity.ok(getAllUsersMembers.getAllUsers());
    }
}
