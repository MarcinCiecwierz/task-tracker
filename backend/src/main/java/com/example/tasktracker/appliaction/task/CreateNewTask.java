package com.example.tasktracker.appliaction.task;

import com.example.tasktracker.DTO.TaskCreateRequest;
import com.example.tasktracker.model.Task;
import com.example.tasktracker.model.Users;
import com.example.tasktracker.repositories.TaskRepository;
import com.example.tasktracker.repositories.UsersRepository;
import jakarta.transaction.Transactional;
import org.hibernate.Hibernate;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CreateNewTask {

    private TaskRepository taskRepository;
    private UsersRepository usersRepository;

    public CreateNewTask(TaskRepository taskRepository, UsersRepository usersRepository) {
        this.taskRepository = taskRepository;
        this.usersRepository = usersRepository;
    }

    @Transactional
    public void createNewTask(TaskCreateRequest taskCreateRequest) {
        if(taskCreateRequest.getTitle() == null || taskCreateRequest.getTitle().isBlank()){
            throw new IllegalArgumentException("Title cannot be blank or null");
        }

        Task task = new Task();
        task.setTitle(taskCreateRequest.getTitle());
        task.setDescription(taskCreateRequest.getDescription());

        List<Users> usersList = usersRepository.findAllById(taskCreateRequest.getUsersIds());

        if(usersList.isEmpty()){
           throw new IllegalArgumentException("Cannot find provided users");
        }

        for(Users user : usersList){
            user.getTasks().add(task);
        }

        try{
            taskRepository.save(task);
            usersRepository.saveAll(task.getUsers());
        } catch (DataAccessException e) {
            throw new IllegalArgumentException("Task could not be saved", e);
        }
    }

}
