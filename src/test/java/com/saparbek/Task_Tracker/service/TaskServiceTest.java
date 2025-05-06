package com.saparbek.Task_Tracker.service;

import com.saparbek.Task_Tracker.dto.TaskRequest;
import com.saparbek.Task_Tracker.model.Task;
import com.saparbek.Task_Tracker.model.TaskStatus;
import com.saparbek.Task_Tracker.repository.TaskRepository;
import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class TaskServiceTest {

    @Mock
    private TaskRepository taskRepository;

    @InjectMocks
    private TaskService taskService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void getAllTasks_shouldReturnListOfTasks() {
        Task task1 = new Task("Task 1", TaskStatus.TODO, LocalDateTime.now(), LocalDateTime.now());
        Task task2 = new Task("Task 2", TaskStatus.IN_PROGRESS, LocalDateTime.now(), LocalDateTime.now());
        when(taskRepository.findAll()).thenReturn(List.of(task1, task2));

        List<Task> tasks = taskService.getTasks(null);

        assertEquals(2, tasks.size());
        verify(taskRepository).findAll();
    }

    @Test
    void getTaskById_shouldReturnTaskIfExists() {
        Task task = new Task("Test", TaskStatus.TODO, LocalDateTime.now(), LocalDateTime.now());
        when(taskRepository.findById(1L)).thenReturn(Optional.of(task));

        Task result = taskService.getTaskById(1L);

        assertEquals("Test", result.getDescription());
    }

    @Test
    void getTaskById_shouldThrowIfNotFound() {
        when(taskRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(EntityNotFoundException.class, () -> taskService.getTaskById(1L));
    }

    @Test
    void createTask_shouldSaveAndReturnTask() {
        Task task = new Task("New", TaskStatus.TODO, LocalDateTime.now(), LocalDateTime.now());
        when(taskRepository.save(any(Task.class))).thenReturn(task);

        Task result = taskService.createTask("New");

        assertEquals("New", result.getDescription());
    }

    @Test
    void updateTask_shouldUpdateDescription() {
        Task task = new Task("Old", TaskStatus.TODO, LocalDateTime.now(), LocalDateTime.now());
        when(taskRepository.findById(1L)).thenReturn(Optional.of(task));
        when(taskRepository.save(any(Task.class))).thenReturn(task);

        TaskRequest request = new TaskRequest();
        request.setDescription("Updated");

        Task updated = taskService.updateTask(1L, request);

        assertEquals("Updated", updated.getDescription());
    }

    @Test
    void markAsDone_shouldUpdateStatusToDone() {
        Task task = new Task("Test", TaskStatus.TODO, LocalDateTime.now(), LocalDateTime.now());
        when(taskRepository.findById(1L)).thenReturn(Optional.of(task));

        Task result = taskService.markAsDone(1L);

        assertEquals(TaskStatus.DONE, result.getStatus());
    }

    @Test
    void markInProgress_shouldUpdateStatusToInProgress() {
        Task task = new Task("Test", TaskStatus.TODO, LocalDateTime.now(), LocalDateTime.now());
        when(taskRepository.findById(1L)).thenReturn(Optional.of(task));

        Task result = taskService.markInProgress(1L);

        assertEquals(TaskStatus.IN_PROGRESS, result.getStatus());
    }

    @Test
    void deleteTask_shouldRemoveTask() {
        Task task = new Task("To delete", TaskStatus.TODO, LocalDateTime.now(), LocalDateTime.now());
        when(taskRepository.findById(1L)).thenReturn(Optional.of(task));

        String message = taskService.deleteTask(1L);

        assertEquals("Task with ID 1 deleted successfully!", message);
        verify(taskRepository).delete(task);
    }
}