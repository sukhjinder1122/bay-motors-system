package com.baymotors.service;

import com.baymotors.domain.customer.Customer;
import com.baymotors.domain.vehicle.Vehicle;
import com.baymotors.exceptions.DuplicateEntityException;
import com.baymotors.exceptions.EntityNotFoundException;
import com.baymotors.repo.CustomerRepository;
import com.baymotors.repo.VehicleRepository;
import com.baymotors.util.IdGenerator;

public class CustomerService {
    private final CustomerRepository customers;
    private final VehicleRepository vehicles;

    public CustomerService(CustomerRepository customers, VehicleRepository vehicles) {
        this.customers = customers;
        this.vehicles = vehicles;
    }

    public String addCustomer(String name, String phone, String email, boolean registered) {
        String id = IdGenerator.next("C");
        Customer c = new Customer(id, name, phone, email, registered);
        customers.save(c);
        return id;
    }

    public void upgradeToRegistered(String customerId) {
        Customer c = customers.findById(customerId)
                .orElseThrow(() -> new EntityNotFoundException("Customer not found: " + customerId));
        c.setRegistered(true);
    }

    public String addVehicleToCustomer(String customerId, String reg, String model, String mfrName) {
        if (vehicles.existsById(reg)) {
            throw new DuplicateEntityException("Vehicle already exists: " + reg);
        }
        Customer c = customers.findById(customerId)
                .orElseThrow(() -> new EntityNotFoundException("Customer not found: " + customerId));
        Vehicle v = new Vehicle(reg, model, mfrName, c.getId());
        vehicles.save(v);
        c.addVehicleId(v.getId());
        return v.getId();
    }

    public Customer get(String id) {
        return customers.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Customer not found: " + id));
    }

    public CustomerRepository repo() { return customers; }
    public VehicleRepository vehicleRepo() { return vehicles; }
}
