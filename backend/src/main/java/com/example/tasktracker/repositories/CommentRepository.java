package com.example.tasktracker.repositories;

import com.example.tasktracker.model.Comment;
import com.example.tasktracker.model.Task;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CommentRepository extends JpaRepository<Comment, String> {

    List<Comment> findAllByTask(Task task);
}
