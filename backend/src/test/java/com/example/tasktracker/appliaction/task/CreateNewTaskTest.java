package com.example.tasktracker.appliaction.task;

import com.example.tasktracker.DTO.TaskCreateRequest;
import com.example.tasktracker.model.Task;
import com.example.tasktracker.model.Users;
import com.example.tasktracker.repositories.TaskRepository;
import com.example.tasktracker.repositories.UsersRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.dao.DataAccessException;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class CreateNewTaskTest {

    private TaskRepository taskRepository;
    private CreateNewTask createNewTask;
    private UsersRepository usersRepository;

    @BeforeEach
    void setUp() {
        taskRepository = mock(TaskRepository.class);
        usersRepository = mock(UsersRepository.class);
        createNewTask = new CreateNewTask(taskRepository, usersRepository);

    }

    @Test
    void shouldSaveWhenDataIsValid() {
        String testUserId = UUID.randomUUID().toString();

        Users testUser = new Users();
        testUser.setId(testUserId);
        testUser.setUsername("testUser");

        when(usersRepository.findAllById(eq(List.of(testUserId))))
                .thenReturn(List.of(testUser));

        TaskCreateRequest request = new TaskCreateRequest();
        request.setDescription("description");
        request.setTitle("Test title");
        request.setUsersIds(List.of(testUserId));

        createNewTask.createNewTask(request);

        verify(taskRepository, times(1)).save(any(Task.class));
    }

    @Test
    void shouldThrowExceptionWhenTitleIsNull() {
        TaskCreateRequest request = new TaskCreateRequest();
        request.setTitle(null);

        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            createNewTask.createNewTask(request);
        });

        assertEquals("Title cannot be blank or null", exception.getMessage());
    }

    @Test
    void shouldThrowExceptionWhenTitleIsBlank() {
        TaskCreateRequest request = new TaskCreateRequest();
        request.setTitle("   ");

        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            createNewTask.createNewTask(request);
        });

        assertEquals("Title cannot be blank or null", exception.getMessage());
    }

    @Test
    void shouldThrowExceptionWhenSavingTaskFails() {
        String testUserId = UUID.randomUUID().toString();

        Users testUser = new Users();
        testUser.setId(testUserId);
        testUser.setUsername("testUser");

        when(usersRepository.findAllById(eq(List.of(testUserId))))
                .thenReturn(List.of(testUser));

        TaskCreateRequest request = new TaskCreateRequest();
        request.setDescription("description");
        request.setTitle("Test title");
        request.setUsersIds(List.of(testUserId));

        when(taskRepository.save(any(Task.class))).thenThrow(new DataAccessException("Db error") {
        });

        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            createNewTask.createNewTask(request);
        });

        assertEquals("Task could not be saved", exception.getMessage());
    }
}