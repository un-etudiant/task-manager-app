package com.taskmanager.task_manager_service.repository;

import com.taskmanager.task_manager_service.model.Task;
import org.springframework.stereotype.Repository;
import software.amazon.awssdk.enhanced.dynamodb.DynamoDbEnhancedClient;
import software.amazon.awssdk.enhanced.dynamodb.DynamoDbTable;
import software.amazon.awssdk.enhanced.dynamodb.Key;
import software.amazon.awssdk.enhanced.dynamodb.TableSchema;
import software.amazon.awssdk.enhanced.dynamodb.model.QueryConditional;
import software.amazon.awssdk.enhanced.dynamodb.model.QueryEnhancedRequest;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Repository
public class TaskRepository {

    private final DynamoDbTable<Task> taskTable;

    public TaskRepository(DynamoDbEnhancedClient enhancedClient) {
        this.taskTable = enhancedClient.table("Tasks", TableSchema.fromBean(Task.class));
    }

    public Task save(Task task) {
        taskTable.putItem(task);
        return task;
    }

    public Optional<Task> findByUserIdAndTaskId(String userId, String taskId) {
        Key key = Key.builder()
                .partitionValue(userId)
                .sortValue(taskId)
                .build();
        return Optional.ofNullable(taskTable.getItem(key));
    }

    public List<Task> findAllByUserId(String userId) {
        QueryEnhancedRequest queryRequest = QueryEnhancedRequest
                .builder()
                .queryConditional(QueryConditional.keyEqualTo(Key.builder()
                        .partitionValue(userId)
                        .build()))
                .build();

        List<Task> tasks = new ArrayList<>();
        taskTable.query(queryRequest).items().forEach(tasks::add);
        return tasks;
    }

    public void delete(String userId, String taskId) {
        Key key = Key.builder()
                .partitionValue(userId)
                .sortValue(taskId)
                .build();
        taskTable.deleteItem(key);
    }

    public boolean exists(String userId, String taskId) {
        Key key = Key.builder()
                .partitionValue(userId)
                .sortValue(taskId)
                .build();
        return taskTable.getItem(key) != null;
    }
}