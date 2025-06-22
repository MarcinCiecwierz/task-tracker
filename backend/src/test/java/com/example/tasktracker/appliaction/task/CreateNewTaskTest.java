//package com.example.tasktracker.appliaction.task;
//
//import com.example.tasktracker.DTO.TaskCreateRequest;
//import com.example.tasktracker.model.Task;
//import com.example.tasktracker.repositories.TaskRepository;
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.Test;
//import org.springframework.dao.DataAccessException;
//
//import static org.junit.jupiter.api.Assertions.*;
//import static org.mockito.Mockito.*;
//
//class CreateNewTaskTest {
//
//    private TaskRepository taskRepository;
//    private CreateNewTask createNewTask;
//
//    @BeforeEach
//    void setUp() {
//        taskRepository = mock(TaskRepository.class);
//        createNewTask = new CreateNewTask(taskRepository);
//    }
//
//    @Test
//    void shouldSaveWhenDataIsValid() {
//
//        TaskCreateRequest request = new TaskCreateRequest();
//        request.setDescription("description");
//        request.setTitle("Test title");
//
//        createNewTask.createNewTask(request);
//
//        verify(taskRepository, times(1)).save(any(Task.class));
//    }
//
//    @Test
//    void shouldThrowExceptionWhenTitleIsNull() {
//        TaskCreateRequest request = new TaskCreateRequest();
//        request.setTitle(null);
//
//        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
//            createNewTask.createNewTask(request);
//        });
//
//        assertEquals("Title cannot be blank or null", exception.getMessage());
//    }
//
//    @Test
//    void shouldThrowExceptionWhenTitleIsBlank() {
//        TaskCreateRequest request = new TaskCreateRequest();
//        request.setTitle("   ");
//
//        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
//            createNewTask.createNewTask(request);
//        });
//
//        assertEquals("Title cannot be blank or null", exception.getMessage());
//    }
//
//    @Test
//    void shouldThrowExceptionWhenSavingTaskFails() {
//        TaskCreateRequest request = new TaskCreateRequest();
//        request.setDescription("description");
//        request.setTitle("Test title");
//
//        when(taskRepository.save(any(Task.class))).thenThrow(new DataAccessException("Db error") {
//        });
//
//        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
//            createNewTask.createNewTask(request);
//        });
//
//        assertEquals("Task could not be saved", exception.getMessage());
//    }
//}