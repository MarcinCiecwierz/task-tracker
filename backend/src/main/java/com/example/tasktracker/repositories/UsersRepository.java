package com.example.tasktracker.repositories;

import com.example.tasktracker.model.Task;
import com.example.tasktracker.model.Users;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.Set;

@Repository
public interface UsersRepository extends JpaRepository<Users, String> {
    Optional<Users> findUsersByAuth0Id(String auth0_id);
    List<Users> findAllByRole(String role);
    List<Users> findAllByTasks(Set<Task> tasks);
}
