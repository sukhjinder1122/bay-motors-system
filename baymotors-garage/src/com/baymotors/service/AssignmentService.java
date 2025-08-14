package com.baymotors.service;

import java.util.ArrayList;
import java.util.List;

import com.baymotors.domain.customer.Customer;
import com.baymotors.domain.task.Task;
import com.baymotors.domain.task.TaskPriority;
import com.baymotors.domain.task.TaskStatus;
import com.baymotors.domain.vehicle.Vehicle;
import com.baymotors.exceptions.EntityNotFoundException;
import com.baymotors.exceptions.InvalidOperationException;
import com.baymotors.repo.TaskRepository;
import com.baymotors.repo.VehicleRepository;
import com.baymotors.util.IdGenerator;

public class AssignmentService {
    private final TaskRepository tasks;
    private final VehicleRepository vehicles;
    private final CustomerService customerService;
    private final NotificationService notifications;

    public AssignmentService(TaskRepository tasks, VehicleRepository vehicles,
                             CustomerService customerService, NotificationService notifications) {
        this.tasks = tasks;
        this.vehicles = vehicles;
        this.customerService = customerService;
        this.notifications = notifications;
    }

    public String createTask(String vehicleId, String description, TaskPriority priority) {
        Vehicle v = vehicles.findById(vehicleId)
                .orElseThrow(() -> new EntityNotFoundException("Vehicle not found: " + vehicleId));
        String id = IdGenerator.next("T");
        Task t = new Task(id, v.getId(), description, priority);
        tasks.save(t);
        v.addTaskId(id);
        return id;
    }

    public Task assignNextWaiting(String mechanicId) {
        List<Task> waiting = tasks.waiting();
        if (waiting.isEmpty()) return null;
        Task t = waiting.get(0);
        t.setAssignedMechanicId(mechanicId);
        t.setStatus(TaskStatus.ASSIGNED);
        return t;
    }

    public List<Task> tasksForMechanic(String mechId) {
        return new ArrayList<>(tasks.forMechanic(mechId));
    }

    public void startTask(String taskId, String mechId) {
        Task t = tasks.findById(taskId).orElseThrow(() -> notFound(taskId));
        if (!mechId.equals(t.getAssignedMechanicId()))
            throw new InvalidOperationException("Task not assigned to this mechanic.");
        if (t.getStatus() != TaskStatus.ASSIGNED)
            throw new InvalidOperationException("Task not in ASSIGNED state.");
        t.setStatus(TaskStatus.IN_PROGRESS);
    }

    public void completeTask(String taskId, String mechId, String notes) {
        Task t = tasks.findById(taskId).orElseThrow(() -> notFound(taskId));
        if (!mechId.equals(t.getAssignedMechanicId()))
            throw new InvalidOperationException("Task not assigned to this mechanic.");
        if (t.getStatus() != TaskStatus.IN_PROGRESS)
            throw new InvalidOperationException("Task must be IN_PROGRESS.");
        t.setStatus(TaskStatus.DONE);
        t.setCompletionNotes(notes);

        Vehicle v = vehicles.findById(t.getVehicleId()).orElseThrow(() -> new EntityNotFoundException("Vehicle not found."));
        Customer c = customerService.get(v.getCustomerId());
        notifications.notifyCustomer(c, "Your vehicle " + v.getId() + " is ready for pickup.");
    }

    public List<Task> allTasks() {
        return tasks.findAll();
    }

    private EntityNotFoundException notFound(String id) {
        return new EntityNotFoundException("Task not found: " + id);
    }
}
