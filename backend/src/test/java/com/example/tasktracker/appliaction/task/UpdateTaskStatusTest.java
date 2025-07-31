package com.example.tasktracker.appliaction.task;

import com.example.tasktracker.DTO.UpdateTaskDTO;
import com.example.tasktracker.model.Task;
import com.example.tasktracker.repositories.TaskRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class UpdateTaskStatusTest {

    private TaskRepository taskRepository;
    private UpdateTaskStatus updateTaskStatus;

    private String taskId;
    private Task task;

    @BeforeEach
    void setUp() {
        taskRepository = mock(TaskRepository.class);
        updateTaskStatus = new UpdateTaskStatus(taskRepository);
    }

    @BeforeEach
    void setUpData(){
        taskId = UUID.randomUUID().toString();

        task = new Task();
        task.setId(taskId);
        task.setTitle("Task Title");
        task.setDescription("Task Description");
        task.setStatus("Done");
    }

    @Test
    void shouldUpdateTaskStatus(){
        when(taskRepository.findById(taskId)).thenReturn(Optional.of(task));

        UpdateTaskDTO request = new UpdateTaskDTO(taskId, "To-Do");

        updateTaskStatus.update(request);

        assertEquals(task.getStatus(), request.getStatus());
    }

    @Test
    void shouldNotUpdateTaskStatusBlankStatus(){
        when(taskRepository.findById(taskId)).thenReturn(Optional.of(task));

        UpdateTaskDTO request = new UpdateTaskDTO(taskId, " ");

        assertThrows(IllegalArgumentException.class, () -> updateTaskStatus.update(request));
    }

    @Test
    void shouldNotFindTask(){
        when(taskRepository.findById("123123")).thenReturn(Optional.empty());

        UpdateTaskDTO request = new UpdateTaskDTO(taskId, "To-Do");

        assertThrows(IllegalArgumentException.class, () -> updateTaskStatus.update(request));
    }
}