package com.example.tasktracker.appliaction.task;

import com.example.tasktracker.DTO.GetInfoAboutTaskDTO;
import com.example.tasktracker.model.Comment;
import com.example.tasktracker.model.Task;
import com.example.tasktracker.model.Users;
import com.example.tasktracker.repositories.CommentRepository;
import com.example.tasktracker.repositories.TaskRepository;
import com.example.tasktracker.repositories.UsersRepository;
import org.apache.catalina.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class GetInfoAboutTaskTest {

    private TaskRepository taskRepository;
    private UsersRepository usersRepository;
    private CommentRepository commentRepository;
    private GetInfoAboutTask getInfoAboutTask;

    private String taskId;
    private Task task;
    private Comment comment;
    private Users user;

    @BeforeEach
    void setUp() {
        taskRepository = mock(TaskRepository.class);
        usersRepository = mock(UsersRepository.class);
        commentRepository = mock(CommentRepository.class);
        getInfoAboutTask = new GetInfoAboutTask(taskRepository, usersRepository, commentRepository);
    }

    @BeforeEach
    void setUpData() {
        taskId = UUID.randomUUID().toString();

        task = new Task();
        task.setId(taskId);
        task.setTitle("Task Title");
        task.setDescription("Task Description");

        comment = new Comment();
        comment.setId(UUID.randomUUID().toString());
        comment.setCommentText("Comment Text");

        user = new Users();
        user.setId(UUID.randomUUID().toString());
        user.setUsername("username");

    }

    @Test
    void shouldReturnInfoAboutTask() {
        comment.setUsers(user);
        task.setUsers(Set.of(user));
        task.setComments(Set.of(comment));

        when(taskRepository.findById(taskId)).thenReturn(Optional.of(task));

        when(usersRepository.findAllByTasks(Set.of(task)))
                .thenReturn(List.of(user));

        when(commentRepository.findAllByTask(task))
                .thenReturn(List.of(comment));

        GetInfoAboutTaskDTO result = getInfoAboutTask.get(taskId);

        assertNotNull(result);
        assertEquals("Task Title", result.getTitle());
        assertEquals("Task Description", result.getDescription());
        assertEquals(task.getUsers().size(), result.getUsers().size());
        assertEquals(task.getComments().size(), result.getComments().size());
    }

    @Test
    void shouldReturnExceptionWhenTaskNotFound() {
        when(taskRepository.findById("123123")).thenReturn(Optional.empty());
        assertThrows(IllegalArgumentException.class, () -> getInfoAboutTask.get("123123"));
    }

    @Test
    void shouldReturnEmptyUserListWhenNobodyIsAssigned(){
        when(taskRepository.findById(taskId)).thenReturn(Optional.of(task));

        GetInfoAboutTaskDTO result = getInfoAboutTask.get(taskId);

        assertEquals(0, result.getUsers().size());
    }

    @Test
    void shouldReturnEmptyCommentListWhenThereIsNoComments(){
        when(taskRepository.findById(taskId)).thenReturn(Optional.of(task));
        GetInfoAboutTaskDTO result = getInfoAboutTask.get(taskId);
        assertEquals(0, result.getComments().size());
    }
}