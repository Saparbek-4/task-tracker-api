package com.saparbek.Task_Tracker.service;

import com.saparbek.Task_Tracker.dto.TaskRequest;
import com.saparbek.Task_Tracker.model.Task;
import com.saparbek.Task_Tracker.model.TaskStatus;
import com.saparbek.Task_Tracker.model.User;
import com.saparbek.Task_Tracker.repository.TaskRepository;
import com.saparbek.Task_Tracker.repository.UserRepository;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class TaskService {

    private final TaskRepository taskRepository;
    private final UserRepository userRepository;
    private final UserSecurityService userSecurityService;

    public TaskService(TaskRepository taskRepository, UserRepository userRepository, UserSecurityService userSecurityService) {
        this.taskRepository = taskRepository;
        this.userRepository = userRepository;
        this.userSecurityService = userSecurityService;
    }

    public Task getTaskById(Long id) {
        return taskRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Task not found with id: " + id));
    }

    @Transactional
    public Task createTask(String description, String userEmail) {
        User user = userRepository.findByEmail(userEmail)
                .orElseThrow(() -> new EntityNotFoundException("User not found with email " + userEmail));
        Task task = new Task();
        task.setDescription(description);
        task.setStatus(TaskStatus.TODO);
        task.setCreatedAt(LocalDateTime.now());
        task.setUpdatedAt(LocalDateTime.now());
        task.setUser(user);
        return taskRepository.save(task);
    }

    public List<Task> getTasksByUserEmail(String userEmail) {
        return taskRepository.findAllByUserEmail(userEmail);
    }

    public List<Task> getTasks(String status) {
        User user = userSecurityService.getCurrentUser();
        if (userSecurityService.isAdmin()) {
            return (status == null)
                    ? taskRepository.findAll()
                    : taskRepository.findByStatus(TaskStatus.valueOf(status.toUpperCase()));
        }

        return (status == null)
                ? taskRepository.findByUser(user)
                : taskRepository.findByUserAndStatus(
                        user,
                        TaskStatus.valueOf(status.toUpperCase())
        );
    }


    @Transactional
    public Task updateTask(Long id, TaskRequest taskRequest) {
        Task task = getTaskById(id);
        task.setDescription(taskRequest.getDescription());
        task.setUpdatedAt(LocalDateTime.now());

        taskRepository.save(task);

        return task;

    }

    @Transactional
    public Task markAsDone(Long id) {
        Task task = getTaskById(id);
        task.setStatus(TaskStatus.DONE);
        task.setUpdatedAt(LocalDateTime.now());

        taskRepository.save(task);

        return task;
    }

    @Transactional
    public Task markInProgress(Long id) {
        Task task = getTaskById(id);
        task.setStatus(TaskStatus.IN_PROGRESS);
        task.setUpdatedAt(LocalDateTime.now());

        taskRepository.save(task);

        return task;
    }

    @Transactional
    public String deleteTask(Long id) {
        Task task = getTaskById(id);

        taskRepository.delete(task);

        return "Task with ID " + id + " deleted successfully!";
    }
}

