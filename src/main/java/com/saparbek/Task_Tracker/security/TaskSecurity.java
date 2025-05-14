package com.saparbek.Task_Tracker.security;

import com.saparbek.Task_Tracker.model.Task;
import com.saparbek.Task_Tracker.repository.TaskRepository;
import com.saparbek.Task_Tracker.repository.UserRepository;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

@Component("taskSecurity")
public class TaskSecurity {
    private final TaskRepository taskRepository;
    private final UserRepository userRepository;

    public TaskSecurity(TaskRepository taskRepository, UserRepository userRepository) {
        this.taskRepository = taskRepository;
        this.userRepository = userRepository;
    }

    public boolean isOwner(Long taskId) {
        String currentEmail = SecurityContextHolder.getContext().getAuthentication().getName();
        Task task = taskRepository.findById(taskId).orElse(null);
        if (task == null) return false;
        return task.getUser().getEmail().equals(currentEmail);
    }
}
