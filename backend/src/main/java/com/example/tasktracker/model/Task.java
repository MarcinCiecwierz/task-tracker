package com.example.tasktracker.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

@Entity
@Getter
@Setter
public class Task {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;
    private String title;
    private String description;
    private String status = "BackLog";
    private LocalDate createdAt = LocalDate.now();

    @ManyToMany(mappedBy = "tasks")
    private Set<Users> users = new HashSet<>();

    @OneToMany(mappedBy = "task")
    private Set<Comment> comments;
}
