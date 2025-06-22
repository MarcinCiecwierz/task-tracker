package com.example.tasktracker.appliaction.task;

import com.example.tasktracker.DTO.UpdateTaskDTO;
import com.example.tasktracker.model.Task;
import com.example.tasktracker.repositories.TaskRepository;
import com.example.tasktracker.repositories.UsersRepository;
import org.springframework.security.core.parameters.P;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UpdateTaskStatus {

    private TaskRepository taskRepository;

    public UpdateTaskStatus(TaskRepository taskRepository) {
        this.taskRepository = taskRepository;
    }

    public void update(UpdateTaskDTO taskDTO) {
        if(taskDTO.getStatus().isBlank()){
            throw new IllegalArgumentException("Status cannot be blank");
        }

        Optional<Task> task = taskRepository.findById(taskDTO.getTaskId());

        if(task.isEmpty()){
            throw new IllegalArgumentException("Task not found");
        }

        task.get().setStatus(taskDTO.getStatus());
        taskRepository.save(task.get());
    }
}
