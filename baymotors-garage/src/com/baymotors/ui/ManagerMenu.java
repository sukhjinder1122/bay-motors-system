package com.baymotors.ui;

import java.util.List;

import com.baymotors.domain.task.Task;
import com.baymotors.domain.task.TaskPriority;
import com.baymotors.domain.task.TaskStatus;
import com.baymotors.domain.user.Manager;
import com.baymotors.exceptions.EntityNotFoundException;
import com.baymotors.service.GarageSystemFacade;

public class ManagerMenu {
    private final InputReader in;
    private final GarageSystemFacade facade;
    private final Manager manager;

    public ManagerMenu(InputReader in, GarageSystemFacade facade, Manager manager) {
        this.in = in;
        this.facade = facade;
        this.manager = manager;
    }

    public void show() {
        while (true) {
            System.out.println();
            System.out.println("Manager Menu");
            System.out.println("1) Add customer");
            System.out.println("2) Upgrade customer to registered");
            System.out.println("3) Add vehicle to customer");
            System.out.println("4) Create task for vehicle");
            System.out.println("5) Assign next waiting task to mechanic");
            System.out.println("6) Broadcast offer to registered customers");
            System.out.println("7) Broadcast register benefits to non-registered");
            System.out.println("8) Add manufacturer");
            System.out.println("9) Add supplier to manufacturer");
            System.out.println("10) View all tasks");
            System.out.println("0) Back");
            int pick = in.askInt("Choose: ");

            try {
                switch (pick) {
                    case 1 -> addCustomer();
                    case 2 -> upgradeCustomer();
                    case 3 -> addVehicle();
                    case 4 -> createTask();
                    case 5 -> assignTask();
                    case 6 -> offerBlast();
                    case 7 -> inviteBlast();
                    case 8 -> addManufacturer();
                    case 9 -> addSupplier();
                    case 10 -> viewTasks();
                    case 0 -> { return; }
                    default -> System.out.println("Invalid choice.");
                }
            } catch (RuntimeException ex) {
                System.out.println(ex.getMessage());
            }
        }
    }

    private void addCustomer() {
        String name = in.askNonEmpty("Customer name: ");
        String phone = in.askNonEmpty("Phone: ");
        String email = in.askNonEmpty("Email: ");
        String id = facade.customers().addCustomer(name, phone, email, false);
        System.out.println("Customer id: " + id);
    }

    private void upgradeCustomer() {
        String id = in.askNonEmpty("Customer id: ");
        facade.customers().upgradeToRegistered(id);
        System.out.println("Upgraded.");
    }

    private void addVehicle() {
        String custId = in.askNonEmpty("Customer id: ");
        String reg = in.askNonEmpty("Registration/VIN: ");
        String model = in.askNonEmpty("Model: ");
        String mfr = in.askNonEmpty("Manufacturer name: ");
        String vid = facade.customers().addVehicleToCustomer(custId, reg, model, mfr);
        System.out.println("Vehicle id: " + vid);
    }

    private void createTask() {
        String reg = in.askNonEmpty("Vehicle registration/VIN: ");
        String desc = in.askNonEmpty("Task description: ");
        TaskPriority pr = in.askEnum("Priority (HIGH/MEDIUM/LOW): ", TaskPriority.class);
        String tid = facade.assignments().createTask(reg, desc, pr);
        System.out.println("Task id: " + tid);
    }

    private void assignTask() {
        String mechId = in.askNonEmpty("Mechanic id (any string): ");
        Task t = facade.assignments().assignNextWaiting(mechId);
        System.out.println(t == null ? "No waiting tasks." : ("Assigned task " + t.getId()));
    }

    private void offerBlast() {
        String msg = in.askNonEmpty("Offer message: ");
        facade.notifications().broadcastToRegistered(msg);
        System.out.println("Sent.");
    }

    private void inviteBlast() {
        String msg = in.askNonEmpty("Invite message: ");
        facade.notifications().broadcastToNonRegistered(msg);
        System.out.println("Sent.");
    }

    private void addManufacturer() {
        String name = in.askNonEmpty("Manufacturer name: ");
        facade.catalog().addManufacturer(name);
        System.out.println("Added.");
    }

    private void addSupplier() {
        String mfr = in.askNonEmpty("Manufacturer name: ");
        String sup = in.askNonEmpty("Supplier name: ");
        String contact = in.askNonEmpty("Supplier contact: ");
        facade.catalog().addSupplier(mfr, sup, contact);
        System.out.println("Added.");
    }

    private void viewTasks() {
        List<Task> list = facade.assignments().allTasks();
        if (list.isEmpty()) {
            System.out.println("No tasks.");
            return;
        }
        for (Task t : list) {
            System.out.println(
                t.getId() + " [" + t.getStatus() + " " + t.getPriority() + "] " +
                t.getDescription() + " / vehicle " + t.getVehicleId() +
                (t.getAssignedMechanicId() != null ? " / mech " + t.getAssignedMechanicId() : "")
            );
        }
    }
}
