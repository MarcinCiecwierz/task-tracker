package com.example.tasktracker.appliaction.task;

import com.example.tasktracker.DTO.TasksForLoggedUser;
import com.example.tasktracker.model.Task;
import com.example.tasktracker.model.Users;
import com.example.tasktracker.repositories.TaskRepository;
import com.example.tasktracker.repositories.UsersRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class GetAllTasksForLoggedUser {

    private UsersRepository usersRepository;

    private TaskRepository taskRepository;

    public GetAllTasksForLoggedUser(UsersRepository usersRepository, TaskRepository taskRepository) {
        this.usersRepository = usersRepository;
        this.taskRepository = taskRepository;
    }

    public List<TasksForLoggedUser> get(String sub){
        Optional<Users> user = usersRepository.findUsersByAuth0Id(sub);

        if (user.isEmpty()) {
            throw new IllegalArgumentException("User not found");
        }

        List<Task> taskList = taskRepository.findAllByUsers_Id(user.get());
        return taskList.stream().map(task -> new TasksForLoggedUser(task.getId(), task.getTitle(), task.getStatus())).collect(Collectors.toList());
    }
}
