package com.example.tasktracker.appliaction.comment;

import com.example.tasktracker.DTO.RequestNewCommentDTO;
import com.example.tasktracker.model.Comment;
import com.example.tasktracker.model.Task;
import com.example.tasktracker.model.Users;
import com.example.tasktracker.repositories.CommentRepository;
import com.example.tasktracker.repositories.TaskRepository;
import com.example.tasktracker.repositories.UsersRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class AddNewCommentToTask {

    private CommentRepository commentRepository;
    private UsersRepository usersRepository;
    private TaskRepository taskRepository;

    public AddNewCommentToTask(CommentRepository commentRepository, UsersRepository usersRepository, TaskRepository taskRepository) {
        this.commentRepository = commentRepository;
        this.usersRepository = usersRepository;
        this.taskRepository = taskRepository;
    }

    public void addComment(String sub, RequestNewCommentDTO commentDTO){
        if (commentDTO.getText().isBlank()){
            throw new IllegalArgumentException("Text cannot be blank");
        }
        Optional<Users> user = usersRepository.findUsersByAuth0Id(sub);

        if(user.isEmpty()){
            throw new RuntimeException("User not found");
        }

        Optional<Task> task = taskRepository.findById(commentDTO.getTaskId());

        if(task.isEmpty()){
            throw new RuntimeException("Task not found");
        }

        Comment comment = new Comment();
        comment.setCommentText(commentDTO.getText());
        comment.setTask(task.get());
        comment.setUsers(user.get());
        commentRepository.save(comment);
    }
}
