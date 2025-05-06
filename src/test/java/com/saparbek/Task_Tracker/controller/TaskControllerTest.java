package com.saparbek.Task_Tracker.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.saparbek.Task_Tracker.controller.TaskController;
import com.saparbek.Task_Tracker.model.Task;
import com.saparbek.Task_Tracker.model.TaskStatus;
import com.saparbek.Task_Tracker.service.TaskService;
import com.saparbek.Task_Tracker.dto.TaskRequest;

import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDateTime;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


@WebMvcTest(TaskController.class)
class TaskControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private TaskService taskService;

    private Task mockTask;

    @BeforeEach
    void setup() {
        mockTask = new Task();
        mockTask.setId(1L);
        mockTask.setDescription("Test task");
        mockTask.setStatus(TaskStatus.TODO);
        mockTask.setCreatedAt(LocalDateTime.now());
        mockTask.setUpdatedAt(LocalDateTime.now());
    }

    @Test
    void getTasks_shouldReturnAllTasks() throws Exception {
        when(taskService.getTasks(null)).thenReturn(List.of(mockTask));

        mockMvc.perform(get("/tasks"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].description").value("Test task"));
    }

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void createTask_shouldCreateAndReturnTask() throws Exception {
        Task task = new Task();
        task.setId(1L);
        task.setDescription("Test task");
        task.setStatus(TaskStatus.TODO);
        task.setCreatedAt(LocalDateTime.now());
        task.setUpdatedAt(LocalDateTime.now());

        TaskRequest request = new TaskRequest();
        request.setDescription("Test task");

        when(taskService.createTask("Test task")).thenReturn(task);

        mockMvc.perform(post("/tasks")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.description").value("Test task"));
    }


    @Test
    void createTask_shouldReturnBadRequest_whenDescriptionIsBlank() throws Exception {
        mockMvc.perform(post("/tasks")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"description\": \"\"}"))
                .andExpect(status().isBadRequest());
    }

    @Test
    void updateTask_shouldUpdateAndReturnTask() throws Exception {
        when(taskService.updateTask(eq(1L), any(TaskRequest.class))).thenReturn(mockTask);

        mockMvc.perform(put("/tasks/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"description\": \"Updated task\"}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.description").value("Test task"));
    }

    @Test
    void updateTask_shouldReturn404_whenNotFound() throws Exception {
        when(taskService.updateTask(eq(1L), any(TaskRequest.class)))
                .thenThrow(new EntityNotFoundException("Task not found"));

        mockMvc.perform(put("/tasks/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"description\": \"Updated task\"}"))
                .andExpect(status().isBadRequest());
    }

    @Test
    void markTaskAsDone_shouldSetStatusToDone() throws Exception {
        mockTask.setStatus(TaskStatus.DONE);
        when(taskService.markAsDone(1L)).thenReturn(mockTask);

        mockMvc.perform(patch("/tasks/1/done"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.status").value("DONE"));
    }

    @Test
    void markTaskAsDone_shouldReturn404_whenNotFound() throws Exception {
        when(taskService.markAsDone(1L)).thenThrow(new EntityNotFoundException("Task not found"));

        mockMvc.perform(patch("/tasks/1/done"))
                .andExpect(status().isBadRequest());
    }

    @Test
    void deleteTask_shouldDeleteTask() throws Exception {
        when(taskService.deleteTask(1L)).thenReturn("Task with ID 1 deleted successfully!");

        mockMvc.perform(delete("/tasks/1"))
                .andExpect(status().isOk())
                .andExpect(content().string("Task with ID 1 deleted successfully!"));
    }

    @Test
    void deleteTask_shouldReturn404_whenNotFound() throws Exception {
        when(taskService.deleteTask(1L)).thenThrow(new EntityNotFoundException("Task not found"));

        mockMvc.perform(delete("/tasks/1"))
                .andExpect(status().isBadRequest());
    }
}