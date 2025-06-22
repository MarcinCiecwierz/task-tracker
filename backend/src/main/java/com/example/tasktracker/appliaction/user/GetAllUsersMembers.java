package com.example.tasktracker.appliaction.user;

import com.example.tasktracker.DTO.UserToCreateTask;
import com.example.tasktracker.model.Users;
import com.example.tasktracker.repositories.UsersRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class GetAllUsersMembers {

    private UsersRepository usersRepository;

    public GetAllUsersMembers(UsersRepository usersRepository) {
        this.usersRepository = usersRepository;
    }

    public List<UserToCreateTask> getAllUsers() {
        List <Users> users = usersRepository.findAllByRole("Member");
        return users.stream().map(
                user -> new UserToCreateTask(user.getId(), user.getUsername(), user.getRole()))
                .collect(Collectors.toList());
    }
}

