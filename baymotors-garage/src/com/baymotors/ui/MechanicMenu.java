package com.baymotors.ui;

import java.util.List;

import com.baymotors.domain.task.Task;
import com.baymotors.domain.user.Mechanic;
import com.baymotors.service.GarageSystemFacade;

public class MechanicMenu {
    private final InputReader in;
    private final GarageSystemFacade facade;
    private final Mechanic mechanic;

    public MechanicMenu(InputReader in, GarageSystemFacade facade, Mechanic mechanic) {
        this.in = in;
        this.facade = facade;
        this.mechanic = mechanic;
    }

    public void show() {
        while (true) {
            System.out.println();
            System.out.println("Mechanic Menu");
            System.out.println("1) View my queue");
            System.out.println("2) Start a task");
            System.out.println("3) Complete a task");
            System.out.println("4) Log walk-in (customer + vehicle)");
            System.out.println("0) Back");
            int pick = in.askInt("Choose: ");
            try {
                switch (pick) {
                    case 1 -> listMyTasks();
                    case 2 -> startTask();
                    case 3 -> completeTask();
                    case 4 -> logWalkIn();
                    case 0 -> { return; }
                    default -> System.out.println("Invalid choice.");
                }
            } catch (RuntimeException ex) {
                System.out.println(ex.getMessage());
            }
        }
    }

    private void listMyTasks() {
        List<Task> tasks = facade.assignments().tasksForMechanic(mechanic.getId());
        if (tasks.isEmpty()) {
            System.out.println("No tasks assigned.");
            return;
        }
        for (Task t : tasks) {
            System.out.println(t.getId() + " [" + t.getStatus() + " " + t.getPriority() + "] " + t.getDescription());
        }
    }

    private void startTask() {
        String id = in.askNonEmpty("Task id: ");
        facade.assignments().startTask(id, mechanic.getId());
        System.out.println("Started.");
    }

    private void completeTask() {
        String id = in.askNonEmpty("Task id: ");
        String notes = in.askNonEmpty("Completion notes: ");
        facade.assignments().completeTask(id, mechanic.getId(), notes);
        System.out.println("Completed. Customer notified.");
    }

    private void logWalkIn() {
        String name = in.askNonEmpty("Customer name: ");
        String phone = in.askNonEmpty("Phone: ");
        String email = in.askNonEmpty("Email: ");
        String reg = in.askNonEmpty("Vehicle registration/VIN: ");
        String model = in.askNonEmpty("Model: ");
        String mfr = in.askNonEmpty("Manufacturer: ");
        String custId = facade.customers().addCustomer(name, phone, email, false);
        facade.customers().addVehicleToCustomer(custId, reg, model, mfr);
        System.out.println("Walk-in saved. Customer id: " + custId);
    }
}
