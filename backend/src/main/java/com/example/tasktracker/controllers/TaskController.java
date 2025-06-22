package com.example.tasktracker.controllers;

import com.example.tasktracker.DTO.TaskCreateRequest;
import com.example.tasktracker.DTO.UpdateTaskDTO;
import com.example.tasktracker.appliaction.task.CreateNewTask;
import com.example.tasktracker.appliaction.task.GetAllTasksForLoggedUser;
import com.example.tasktracker.appliaction.task.GetInfoAboutTask;
import com.example.tasktracker.appliaction.task.UpdateTaskStatus;
import org.apache.coyote.Response;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/task")
public class TaskController {

    private CreateNewTask createNewTask;
    private GetAllTasksForLoggedUser getAllTasksForLoggedUser;
    private GetInfoAboutTask getInfoAboutTask;
    private UpdateTaskStatus updateTaskStatus;

    public TaskController(CreateNewTask createNewTask, GetAllTasksForLoggedUser getAllTasksForLoggedUser,
                          GetInfoAboutTask getInfoAboutTask, UpdateTaskStatus updateTaskStatus) {
        this.createNewTask = createNewTask;
        this.getAllTasksForLoggedUser = getAllTasksForLoggedUser;
        this.getInfoAboutTask = getInfoAboutTask;
        this.updateTaskStatus = updateTaskStatus;
    }

    @PostMapping
    public ResponseEntity<?> createTask(@RequestBody TaskCreateRequest taskCreateRequest) {
        createNewTask.createNewTask(taskCreateRequest);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @GetMapping("/list")
    public ResponseEntity<?> getAllTasksForLoggedUser(@AuthenticationPrincipal Jwt jwt) {
        return ResponseEntity.ok(getAllTasksForLoggedUser.get(jwt.getClaim("sub")));
    }

    @GetMapping
    public ResponseEntity<?> getTaskInfo(@RequestParam String id) {
        return ResponseEntity.ok(getInfoAboutTask.get(id));
    }

    @PutMapping
    public ResponseEntity<?> updateTask(@RequestBody UpdateTaskDTO taskDTO){
        updateTaskStatus.update(taskDTO);
        return ResponseEntity.ok().build();
    }
}
