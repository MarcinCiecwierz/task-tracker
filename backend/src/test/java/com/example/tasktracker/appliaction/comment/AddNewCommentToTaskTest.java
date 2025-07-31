package com.example.tasktracker.appliaction.comment;

import com.example.tasktracker.DTO.RequestNewCommentDTO;
import com.example.tasktracker.model.Comment;
import com.example.tasktracker.model.Task;
import com.example.tasktracker.model.Users;
import com.example.tasktracker.repositories.CommentRepository;
import com.example.tasktracker.repositories.TaskRepository;
import com.example.tasktracker.repositories.UsersRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Optional;
import java.util.Set;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class AddNewCommentToTaskTest {

    private CommentRepository commentRepository;
    private UsersRepository usersRepository;
    private TaskRepository taskRepository;
    private AddNewCommentToTask addNewCommentToTask;

    private String userSub;
    private Users user;
    private Task task;

    @BeforeEach
    void setUp() {
        commentRepository = mock(CommentRepository.class);
        usersRepository = mock(UsersRepository.class);
        taskRepository = mock(TaskRepository.class);
        addNewCommentToTask = new AddNewCommentToTask(commentRepository, usersRepository, taskRepository);
    }

    @BeforeEach
    void setUpData(){
        userSub = "sub";

        user = new Users();
        user.setAuth0Id(userSub);

        task = new Task();
        task.setId(UUID.randomUUID().toString());
        task.setTitle("title");
        task.setDescription("description");
        task.setComments(Set.of());
    }

   @Test
    void shouldAddNewCommentToTask() {
        when(usersRepository.findUsersByAuth0Id(userSub)).thenReturn(Optional.of(user));
        when(taskRepository.findById(task.getId())).thenReturn(Optional.of(task));

       RequestNewCommentDTO request = new RequestNewCommentDTO("this is a new comment", task.getId());

       addNewCommentToTask.addComment(userSub, request);

       verify(commentRepository, times(1)).save(any(Comment.class));
   }

   @Test
    void shouldNotAddNewCommentToTask() {
        when(usersRepository.findUsersByAuth0Id(userSub)).thenReturn(Optional.of(user));
        when(taskRepository.findById(task.getId())).thenReturn(Optional.of(task));

        RequestNewCommentDTO request = new RequestNewCommentDTO(" ", task.getId());

        assertThrows(IllegalArgumentException.class, () -> addNewCommentToTask.addComment(userSub, request));
   }

   @Test
    void shouldThrowExceptionWhenUserNotFound() {
        when(usersRepository.findUsersByAuth0Id("123123")).thenReturn(Optional.empty());

        RequestNewCommentDTO request = new RequestNewCommentDTO("this is a new comment", task.getId());

        assertThrows(RuntimeException.class, () -> addNewCommentToTask.addComment(userSub, request));
   }

   @Test
    void shouldThrowExceptionWhenTaskNotFound() {
        when(usersRepository.findUsersByAuth0Id(userSub)).thenReturn(Optional.of(user));
        when(taskRepository.findById("abc")).thenReturn(Optional.empty());

       RequestNewCommentDTO request = new RequestNewCommentDTO("this is a new comment", task.getId());

       assertThrows(RuntimeException.class, () -> addNewCommentToTask.addComment(userSub, request));
   }
}