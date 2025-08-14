package com.baymotors.domain.vehicle;

import java.util.ArrayList;
import java.util.List;

public class Vehicle {
    private final String id;          // regNo/VIN acts as id here
    private final String model;
    private final String manufacturerName;
    private final String customerId;
    private final List<String> taskIds = new ArrayList<>();

    public Vehicle(String id, String model, String manufacturerName, String customerId) {
        this.id = id;
        this.model = model;
        this.manufacturerName = manufacturerName;
        this.customerId = customerId;
    }

    public String getId() { return id; }
    public String getModel() { return model; }
    public String getManufacturerName() { return manufacturerName; }
    public String getCustomerId() { return customerId; }
    public List<String> getTaskIds() { return taskIds; }
    public void addTaskId(String taskId) { taskIds.add(taskId); }
}
