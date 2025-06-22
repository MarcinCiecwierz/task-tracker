package com.example.tasktracker.appliaction.task;

import com.example.tasktracker.DTO.CommentDTO;
import com.example.tasktracker.DTO.GetInfoAboutTaskDTO;
import com.example.tasktracker.model.Comment;
import com.example.tasktracker.model.Task;
import com.example.tasktracker.model.Users;
import com.example.tasktracker.repositories.CommentRepository;
import com.example.tasktracker.repositories.TaskRepository;
import com.example.tasktracker.repositories.UsersRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class GetInfoAboutTask {

    private TaskRepository taskRepository;
    private UsersRepository usersRepository;
    private CommentRepository commentRepository;

    public GetInfoAboutTask(TaskRepository taskRepository, UsersRepository usersRepository, CommentRepository commentRepository) {
        this.taskRepository = taskRepository;
        this.usersRepository = usersRepository;
        this.commentRepository = commentRepository;
    }

    public GetInfoAboutTaskDTO get(String id) {
        Optional<Task> task = taskRepository.findById(id);

        if (task.isEmpty()) {
            throw new IllegalArgumentException("Task not found");
        }

        GetInfoAboutTaskDTO taskDTO = new GetInfoAboutTaskDTO();

        taskDTO.setTitle(task.get().getTitle());
        taskDTO.setDescription(task.get().getDescription());

        List<String> tasksToString = usersRepository
                .findAllByTasks(Set.of(task.get()))
                .stream()
                .map(Users::getUsername)
                .toList();

        taskDTO.setUsers(tasksToString);

        List<CommentDTO> comments = commentRepository.findAllByTask(task.get())
                .stream()
                .map(comment -> new CommentDTO(comment.getCommentText(), comment.getCreatedAt(), comment.getUsers().getUsername()))
                .collect(Collectors.toList());
        taskDTO.setComments(comments);


        return taskDTO;
    }
}
