package com.baymotors.notification.channels;

import com.baymotors.notification.NotificationChannel;

public class ConsoleChannel implements NotificationChannel {
    @Override
    public void send(String to, String message) {
        System.out.println("Message to " + to + ": " + message);
    }
}
