package com.baymotors.service;

import com.baymotors.repo.CustomerRepository;
import com.baymotors.repo.ManufacturerRepository;
import com.baymotors.repo.TaskRepository;
import com.baymotors.repo.VehicleRepository;

public class GarageSystemFacade {

    private final CustomerRepository customers = new CustomerRepository();
    private final VehicleRepository vehicles = new VehicleRepository();
    private final TaskRepository tasks = new TaskRepository();
    private final ManufacturerRepository manufacturers = new ManufacturerRepository();

    private final CustomerService customerService = new CustomerService(customers, vehicles);
    private final CatalogService catalogService = new CatalogService(manufacturers);
    private final NotificationService notificationService = new NotificationService(customers);
    private final AssignmentService assignmentService =
            new AssignmentService(tasks, vehicles, customerService, notificationService);

    public CustomerService customers() { return customerService; }
    public CatalogService catalog() { return catalogService; }
    public NotificationService notifications() { return notificationService; }
    public AssignmentService assignments() { return assignmentService; }
}
