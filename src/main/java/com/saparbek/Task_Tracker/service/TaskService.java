package com.saparbek.Task_Tracker.service;

import com.saparbek.Task_Tracker.dto.TaskRequest;
import com.saparbek.Task_Tracker.model.Task;
import com.saparbek.Task_Tracker.model.TaskStatus;
import com.saparbek.Task_Tracker.repository.TaskRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class TaskService {

    private final TaskRepository taskRepository;

    public TaskService(TaskRepository taskRepository) {
        this.taskRepository = taskRepository;
    }

    public Task getTaskById(Long id) {
        return taskRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Task not found with id: " + id));
    }

    public Task createTask(String description) {
        Task task = new Task();
        task.setDescription(description);
        task.setStatus(TaskStatus.TODO);
        task.setCreatedAt(LocalDateTime.now());
        task.setUpdatedAt(LocalDateTime.now());
        return taskRepository.save(task);
    }

    public List<Task> getTasks(String status) {
        if (status == null) {
            return taskRepository.findAll();
        }

        try {
            TaskStatus taskStatus = TaskStatus.valueOf(status.toUpperCase());
            return taskRepository.findByStatus(taskStatus);
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException("Status " + status + " is not valid!");
        }
    }

    public Task updateTask(Long id, TaskRequest taskRequest) {
        Task task = getTaskById(id);
        task.setDescription(taskRequest.getDescription());
        task.setUpdatedAt(LocalDateTime.now());

        taskRepository.save(task);

        return task;

    }

    public Task markAsDone(Long id) {
        Task task = getTaskById(id);
        task.setStatus(TaskStatus.DONE);
        task.setUpdatedAt(LocalDateTime.now());

        taskRepository.save(task);

        return task;
    }

    public Task markInProgress(Long id) {
        Task task = getTaskById(id);
        task.setStatus(TaskStatus.IN_PROGRESS);
        task.setUpdatedAt(LocalDateTime.now());

        taskRepository.save(task);

        return task;
    }

    public String deleteTask(Long id) {
        Task task = getTaskById(id);

        taskRepository.delete(task);

        return "Task with ID " + id + " deleted successfully!";
    }
}

