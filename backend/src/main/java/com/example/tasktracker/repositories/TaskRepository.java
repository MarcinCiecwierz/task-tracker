package com.example.tasktracker.repositories;

import com.example.tasktracker.model.Task;
import com.example.tasktracker.model.Users;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TaskRepository extends JpaRepository<Task, String> {

    @Query("SELECT t FROM Task t JOIN FETCH t.users u WHERE u = :user")
    List<Task> findAllByUsers_Id(@Param("user") Users user);

}
