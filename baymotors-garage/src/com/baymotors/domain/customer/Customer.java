package com.baymotors.domain.customer;

import java.util.ArrayList;
import java.util.List;

import com.baymotors.notification.Notifiable;
import com.baymotors.notification.Notification;

public class Customer implements Notifiable {
    private final String id;
    private final String name;
    private final String phone;
    private final String email;
    private boolean registered;
    private final List<String> vehicleIds = new ArrayList<>();

    public Customer(String id, String name, String phone, String email, boolean registered) {
        this.id = id;
        this.name = name;
        this.phone = phone;
        this.email = email;
        this.registered = registered;
    }

    public String getId() { return id; }
    public String getName() { return name; }
    public String getPhone() { return phone; }
    public String getEmail() { return email; }
    public boolean isRegistered() { return registered; }
    public void setRegistered(boolean registered) { this.registered = registered; }
    public List<String> getVehicleIds() { return vehicleIds; }
    public void addVehicleId(String id) { vehicleIds.add(id); }

    @Override
    public void notify(Notification n) {
        System.out.println("Notify " + name + " <" + email + ">: " + n.getMessage());
    }
}
