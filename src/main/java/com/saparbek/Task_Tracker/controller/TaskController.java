package com.saparbek.Task_Tracker.controller;

import com.saparbek.Task_Tracker.dto.TaskRequest;
import com.saparbek.Task_Tracker.model.Task;
import com.saparbek.Task_Tracker.model.TaskStatus;
import com.saparbek.Task_Tracker.repository.TaskRepository;
import com.saparbek.Task_Tracker.service.TaskService;
import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
public class TaskController {

    private final TaskService taskService;

    public TaskController(TaskService taskService) {
        this.taskService = taskService;
    }

    @PostMapping("/tasks")
    public Task createTask(@RequestBody @Valid TaskRequest taskRequest) {
        return taskService.createTask(taskRequest.getDescription());
    }

    @GetMapping("/tasks")
    public List<Task> getTasks(@RequestParam(required = false) String status) {
        return taskService.getTasks(status);
    }

    @PutMapping("/tasks/{id}")
    public Task updateTask(@PathVariable Long id, @RequestBody @Valid TaskRequest taskRequest) {
        return taskService.updateTask(id, taskRequest);
    }

    @PatchMapping("/tasks/{id}/done")
    public Task markAsDone(@PathVariable Long id) {
        return taskService.markAsDone(id);
    }

    @PatchMapping("/tasks/{id}/in-progress")
    public Task markInProgress(@PathVariable Long id) {
        return taskService.markInProgress(id);
    }

    @DeleteMapping("/tasks/{id}")
    public String deleteTask(@PathVariable Long id) {
        return taskService.deleteTask(id);
    }
}
