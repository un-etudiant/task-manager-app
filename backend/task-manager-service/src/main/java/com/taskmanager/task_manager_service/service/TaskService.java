package com.taskmanager.task_manager_service.service;


import com.taskmanager.task_manager_service.model.Task;
import com.taskmanager.task_manager_service.repository.TaskRepository;
import com.taskmanager.task_manager_service.exception.TaskNotFoundException;
import com.taskmanager.task_manager_service.exception.TaskValidationException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
public class TaskService {

    private final TaskRepository taskRepository;

    public Task createTask(String userId, Task task) {
        log.info("Creating new task for user: {}", userId);
        validateTask(task);
        
        // Set metadata
        task.setUserId(userId);
        task.setTaskId(UUID.randomUUID().toString());
        task.setCreatedAt(LocalDateTime.now());
        task.setUpdatedAt(LocalDateTime.now());
        
        // Set default status if not provided
        if (task.getStatus() == null || task.getStatus().isEmpty()) {
            log.debug("No status provided, setting to PENDING");
            task.setStatus("PENDING");
        }
        
        // Set default priority if not provided
        if (task.getPriority() == null) {
            log.debug("No priority provided, setting to MEDIUM");
            task.setPriority(3); // Medium priority
        }
        
        Task savedTask = taskRepository.save(task);
        log.info("Task created successfully with id: {}", savedTask.getTaskId());
        return savedTask;
    }

    public Task updateTask(String userId, String taskId, Task taskUpdate) {
        log.info("Updating task with id: {} for user: {}", taskId, userId);
        Task existingTask = getTaskById(userId, taskId);
        
        // Update fields if provided
        if (taskUpdate.getTitle() != null) {
            log.debug("Updating title for task: {}", taskId);
            existingTask.setTitle(taskUpdate.getTitle());
        }
        if (taskUpdate.getDescription() != null) {
            log.debug("Updating description for task {}", taskId);
            existingTask.setDescription(taskUpdate.getDescription());
        }
        if (taskUpdate.getStatus() != null) {
           log.debug("Updating status for task {}", taskId);
            existingTask.setStatus(taskUpdate.getStatus());
        }
        if (taskUpdate.getDueDate() != null) {
            log.debug("Updating due date for task {}", taskId);
            existingTask.setDueDate(taskUpdate.getDueDate());
        }
        if (taskUpdate.getPriority() != null) {
            log.debug("Updating priority for task {}", taskId);
            existingTask.setPriority(taskUpdate.getPriority());
        }
        if (taskUpdate.getTags() != null) {
            log.debug("Updating tags for task {}", taskId);
            existingTask.setTags(taskUpdate.getTags());
        }
        
        existingTask.setUpdatedAt(LocalDateTime.now());

        Task updatedTask = taskRepository.save(existingTask);
        log.info("Task updated successfully with id: {} for user with id :{}", taskId,userId);
        return updatedTask;
    }

    public Task getTaskById(String userId, String taskId) {
        log.debug("Fetching task: {} for user: {}", taskId, userId);
        return taskRepository.findByUserIdAndTaskId(userId, taskId)
                .orElseThrow(() -> {
                    log.error("Task not found with id: {} for user: {}", taskId, userId);
                    return new TaskNotFoundException("Task not found with id: " + taskId);
                });
    }

    public List<Task> getUserTasks(String userId) {
        log.info("Fetching all tasks for user: {}", userId);
        return taskRepository.findAllByUserId(userId);
    }

    public void deleteTask(String userId, String taskId) {
        log.info("Deleting task: {} for user: {}", taskId, userId);
        if (!taskRepository.exists(userId, taskId)) {
            log.error("Task not found for deletion - taskId: {} userId: {}", taskId, userId);
            throw new TaskNotFoundException("Task not found with id: " + taskId);
        }
        taskRepository.delete(userId, taskId);
        log.info("Successfully deleted task: {} for user: {}", taskId, userId);
    }

    private void validateTask(Task task) {
        if (task.getTitle() == null || task.getTitle().trim().isEmpty()) {
            log.error("task Validation failed: Task title is required");
            throw new TaskValidationException("Task title is required");
        }
        
        if (task.getDueDate() != null && task.getDueDate().isBefore(LocalDateTime.now())) {
            log.error("task Validation failed: Due date cannot be in the past");
            throw new TaskValidationException("Due date cannot be in the past");
        }
        
        if (task.getPriority() != null && (task.getPriority() < 1 || task.getPriority() > 5)) {
            log.error("task Validation failed: Priority must be between 1 and 5");
            throw new TaskValidationException("Priority must be between 1 and 5");
        }
    }
}