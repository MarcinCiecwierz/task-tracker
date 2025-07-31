package com.example.tasktracker.appliaction.user;

import com.example.tasktracker.DTO.UserToCreateTask;
import com.example.tasktracker.model.Users;
import com.example.tasktracker.repositories.UsersRepository;
import org.aspectj.lang.annotation.Before;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class GetAllUsersMembersTest {

    private UsersRepository usersRepository;
    private GetAllUsersMembers getAllUsersMembers;

    private Users user1;
    private Users user2;

    @BeforeEach
    void setUp() {
        usersRepository = mock(UsersRepository.class);
        getAllUsersMembers = new GetAllUsersMembers(usersRepository);
    }

    @BeforeEach
    void setUpData(){
        user1 = new Users();
        user1.setRole("Memeber");
        user1.setId("123");

        user2 = new Users();
        user2.setRole("Member");
        user2.setId("456");
    }

    @Test
    void shouldGetAllUsersMembers() {
        when(usersRepository.findAllByRole("Member")).thenReturn(List.of(user1, user2));

        List<UserToCreateTask> response = getAllUsersMembers.getAllUsers();

        assertEquals(2, response.size());
    }

    @Test
    void shouldGetEmptyListOfUsers() {
        when(usersRepository.findAllByRole("Member")).thenReturn(List.of());

        List<UserToCreateTask> response = getAllUsersMembers.getAllUsers();

        assertEquals(0, response.size());
    }

}