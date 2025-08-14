package com.baymotors.notification;

import java.time.Instant;

public class Notification {
    private final String message;
    private final Audience audience;
    private final Instant when;

    public Notification(String message, Audience audience) {
        this.message = message;
        this.audience = audience;
        this.when = Instant.now();
    }

    public String getMessage() { return message; }
    public Audience getAudience() { return audience; }
    public Instant getWhen() { return when; }
}
