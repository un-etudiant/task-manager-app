
package com.taskmanager.task_manager_service.controller;

import com.taskmanager.task_manager_service.model.Task;
import com.taskmanager.task_manager_service.service.TaskService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import jakarta.validation.Valid;
import java.net.URI;
import java.util.List;

@Slf4j
@RestController
@RequestMapping("/api/v1/tasks")
@RequiredArgsConstructor
public class TaskController {

    private final TaskService taskService;

    @PostMapping
    public ResponseEntity<Task> createTask(
            @AuthenticationPrincipal Jwt jwt,
            @Valid @RequestBody Task task) {
        String userId = jwt.getSubject();
        log.info("Received request to create task for user: {}", userId);
        
        Task createdTask = taskService.createTask(userId, task);
        
        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{taskId}")
                .buildAndExpand(createdTask.getTaskId())
                .toUri();
        
        log.info("Task created successfully with id: {}", createdTask.getTaskId());
        return ResponseEntity.created(location).body(createdTask);
    }

    @GetMapping("/{taskId}")
    public ResponseEntity<Task> getTask(
            @AuthenticationPrincipal Jwt jwt,
            @PathVariable String taskId) {
        String userId = jwt.getSubject();
        log.info("Received request to fetch task: {} for user: {}", taskId, userId);
        
        Task task = taskService.getTaskById(userId, taskId);
        return ResponseEntity.ok(task);
    }

    @GetMapping
    public ResponseEntity<List<Task>> getAllTasks(@AuthenticationPrincipal Jwt jwt) {
        String userId = jwt.getSubject();
        log.info("Received request to fetch all tasks for user: {}", userId);
        
        List<Task> tasks = taskService.getUserTasks(userId);
        return ResponseEntity.ok(tasks);
    }

    @PutMapping("/{taskId}")
    public ResponseEntity<Task> updateTask(
            @AuthenticationPrincipal Jwt jwt,
            @PathVariable String taskId,
            @Valid @RequestBody Task taskUpdate) {
        String userId = jwt.getSubject();
        log.info("Received request to update task: {} for user: {}", taskId, userId);
        
        Task updatedTask = taskService.updateTask(userId, taskId, taskUpdate);
        return ResponseEntity.ok(updatedTask);
    }

    @DeleteMapping("/{taskId}")
    public ResponseEntity<Void> deleteTask(
            @AuthenticationPrincipal Jwt jwt,
            @PathVariable String taskId) {
        String userId = jwt.getSubject();
        log.info("Received request to delete task: {} for user: {}", taskId, userId);
        
        taskService.deleteTask(userId, taskId);
        return ResponseEntity.noContent().build();
    }
}