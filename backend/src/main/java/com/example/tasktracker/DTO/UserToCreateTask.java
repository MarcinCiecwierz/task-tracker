package com.example.tasktracker.DTO;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class UserToCreateTask {

    private String id;
    private String name;
    private String role;
}
