package com.saparbek.Task_Tracker.controller;

import com.saparbek.Task_Tracker.dto.TaskRequest;
import com.saparbek.Task_Tracker.model.Task;
import com.saparbek.Task_Tracker.service.UserSecurityService;
import com.saparbek.Task_Tracker.service.TaskService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Tag(name = "Tasks", description = "Task management endpoints")
@RestController
public class TaskController {

    private final TaskService taskService;
    private final UserSecurityService userSecurityService;
    private static final Logger log = LoggerFactory.getLogger(TaskController.class);

    public TaskController(TaskService taskService, UserSecurityService userSecurityService) {
        this.taskService = taskService;
        this.userSecurityService = userSecurityService;
    }

    @Operation(summary = "Create a new task", description = "Creates a task and assigns it to the given user email")
    @PostMapping("/tasks")
    public Task createTask(@RequestBody @Valid TaskRequest taskRequest) {
        log.info("Creating task for user: {}", taskRequest.getUserEmail());
        return taskService.createTask(taskRequest.getDescription(), taskRequest.getUserEmail());
    }

    @Operation(summary = "Get tasks", description = "Returns tasks for the current user, optionally filtered by status")
    @GetMapping("/tasks")
    public List<Task> getTasks(@RequestParam(required = false) String status) {
        return taskService.getTasks(status);
    }


    @Operation(summary = "Get tasks By User Email", description = "Accessible only to admins")
    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/tasks/by-user/{email}")
    public List<Task> getTasksByUserEmail(@PathVariable String email) {
        return taskService.getTasksByUserEmail(email);
    }


    @Operation(summary = "Update task", description = "Updates task by current user or admins")
    @PreAuthorize("hasRole('ADMIN') or @taskSecurity.isOwner(#id)")
    @PutMapping("/tasks/{id}")
    public Task updateTask(@PathVariable Long id, @RequestBody @Valid TaskRequest taskRequest) {
        log.info("Task with id {} updated by {}", id, userSecurityService.getCurrentUser().getEmail());
        return taskService.updateTask(id, taskRequest);
    }


    @Operation(summary = "Mark task as done")
    @PreAuthorize("hasRole('ADMIN') or @taskSecurity.isOwner(#id)")
    @PatchMapping("/tasks/{id}/done")
    public Task markAsDone(@PathVariable Long id) {
        log.info("Task {} marked as DONE by {}", id, userSecurityService.getCurrentUser().getEmail());
        return taskService.markAsDone(id);
    }


    @Operation(summary = "Mark task as in-progress")
    @PreAuthorize("hasRole('ADMIN') or @taskSecurity.isOwner(#id)")
    @PatchMapping("/tasks/{id}/in-progress")
    public Task markInProgress(@PathVariable Long id) {
        log.info("Task {} marked as IN-PROGRESS by {}", id, userSecurityService.getCurrentUser().getEmail());
        return taskService.markInProgress(id);
    }


    @Operation(summary = "Deletes task", description = "Deletes task by the current user or admins")
    @PreAuthorize("hasRole('ADMIN') or @taskSecurity.isOwner(#taskId)")
    @DeleteMapping("/tasks/{taskId}")
    public ResponseEntity<?> deleteTask(@PathVariable Long taskId) {
        log.info("Task with id {} deleted by {}", taskId, userSecurityService.getCurrentUser().getEmail());
        taskService.deleteTask(taskId);
        return ResponseEntity.ok("Deleted successfully");
    }
}
