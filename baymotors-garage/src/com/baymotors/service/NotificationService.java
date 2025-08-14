package com.baymotors.service;

import com.baymotors.domain.customer.Customer;
import com.baymotors.notification.Audience;
import com.baymotors.notification.Notification;
import com.baymotors.notification.channels.ConsoleChannel;
import com.baymotors.repo.CustomerRepository;

public class NotificationService {
    private final CustomerRepository customers;
    private final ConsoleChannel channel = new ConsoleChannel();

    public NotificationService(CustomerRepository customers) {
        this.customers = customers;
    }

    public void notifyCustomer(Customer c, String message) {
        Notification n = new Notification(message, Audience.SINGLE);
        c.notify(n);
        channel.send(c.getEmail(), message);
    }

    public void broadcastToRegistered(String message) {
        Notification n = new Notification(message, Audience.REGISTERED);
        for (Customer c : customers.findByRegistered(true)) c.notify(n);
    }

    public void broadcastToNonRegistered(String message) {
        Notification n = new Notification(message, Audience.NON_REGISTERED);
        for (Customer c : customers.findByRegistered(false)) c.notify(n);
    }
}
