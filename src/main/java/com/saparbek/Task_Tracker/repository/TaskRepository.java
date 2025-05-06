package com.saparbek.Task_Tracker.repository;

import com.saparbek.Task_Tracker.model.Task;
import com.saparbek.Task_Tracker.model.TaskStatus;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TaskRepository extends JpaRepository<Task, Long> {
    List<Task> findByStatus(TaskStatus status);
}
