package com.example.tasktracker.appliaction.task;

import com.example.tasktracker.DTO.TasksForLoggedUser;
import com.example.tasktracker.model.Task;
import com.example.tasktracker.model.Users;
import com.example.tasktracker.repositories.TaskRepository;
import com.example.tasktracker.repositories.UsersRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class GetAllTasksForLoggedUserTest {

    private UsersRepository usersRepository;
    private TaskRepository taskRepository;
    private GetAllTasksForLoggedUser getAllTasksForLoggedUser;

    //Mock Data
    private Task task1;
    private Task task2;
    private Users user;

    @BeforeEach
    void setUp(){
        taskRepository = mock(TaskRepository.class);
        usersRepository = mock(UsersRepository.class);
        getAllTasksForLoggedUser = new GetAllTasksForLoggedUser(usersRepository, taskRepository);
    }

    @BeforeEach
    void setUpData(){
        user = new Users();
        user.setId(UUID.randomUUID().toString());
        user.setUsername("username");
        user.setEmail("email");
        user.setAuth0Id("auth0Id");

        task1 = new Task();
        task1.setId(UUID.randomUUID().toString());
        task1.setTitle("title");
        task1.setDescription("description");

        task2 = new Task();
        task2.setId(UUID.randomUUID().toString());
        task2.setTitle("title2");
        task2.setDescription("description2");
    }

    @Test
    void shouldGetAllTasksForLoggedUser(){

        when(usersRepository.findUsersByAuth0Id(user.getAuth0Id())).thenReturn(Optional.of(user));
        when(taskRepository.findAllByUsers_Id(user)).thenReturn(List.of(task1, task2));

        List<TasksForLoggedUser> result = getAllTasksForLoggedUser.get(user.getAuth0Id());

        assertEquals(2, result.size());

        assertEquals(task1.getId(), result.get(0).getId());
        assertEquals(task1.getTitle(), result.get(0).getTitle());
        assertEquals(task1.getStatus(), result.get(0).getStatus());

        assertEquals(task2.getId(), result.get(1).getId());
        assertEquals(task2.getTitle(), result.get(1).getTitle());
        assertEquals(task2.getStatus(), result.get(1).getStatus());

        verify(usersRepository).findUsersByAuth0Id(user.getAuth0Id());
        verify(taskRepository).findAllByUsers_Id(user);

    }

    @Test
    void shouldThrowExceptionWhenUserNotFound(){
        when(usersRepository.findUsersByAuth0Id("123123")).thenReturn(Optional.empty());
        assertThrows(IllegalArgumentException.class, () -> getAllTasksForLoggedUser.get("123123"));
    }

    @Test
    void shouldReturnEmptyListWhenNoTaskFound(){
        when(usersRepository.findUsersByAuth0Id(user.getAuth0Id())).thenReturn(Optional.of(user));
        when(taskRepository.findAllByUsers_Id(user)).thenReturn(List.of());

        List<TasksForLoggedUser> result = getAllTasksForLoggedUser.get(user.getAuth0Id());

        assertEquals(0, result.size());
    }
}