package com.baymotors.domain.task;

import java.time.Instant;

public class Task {
    private final String id;
    private final String vehicleId;
    private final String description;
    private final TaskPriority priority;
    private TaskStatus status;
    private final Instant createdAt;
    private String assignedMechanicId;
    private String completionNotes;

    public Task(String id, String vehicleId, String description, TaskPriority priority) {
        this.id = id;
        this.vehicleId = vehicleId;
        this.description = description;
        this.priority = priority;
        this.status = TaskStatus.NEW;
        this.createdAt = Instant.now();
    }

    public String getId() { return id; }
    public String getVehicleId() { return vehicleId; }
    public String getDescription() { return description; }
    public TaskPriority getPriority() { return priority; }
    public TaskStatus getStatus() { return status; }
    public void setStatus(TaskStatus status) { this.status = status; }
    public Instant getCreatedAt() { return createdAt; }
    public String getAssignedMechanicId() { return assignedMechanicId; }
    public void setAssignedMechanicId(String id) { this.assignedMechanicId = id; }
    public String getCompletionNotes() { return completionNotes; }
    public void setCompletionNotes(String notes) { this.completionNotes = notes; }
}
