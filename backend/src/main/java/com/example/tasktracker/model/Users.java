package com.example.tasktracker.model;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

@Entity
@Data
public class Users {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;
    private String auth0Id;
    private String username;
    private String email;
    private String role = "Member";
    private LocalDate createdAt = LocalDate.now();

    @ManyToMany()
    @JoinTable(
            name = "task_users",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "task_id")
    )
    private Set<Task> tasks = new HashSet<>();

    @OneToMany(mappedBy = "users")
    private Set<Comment> comments;
}
