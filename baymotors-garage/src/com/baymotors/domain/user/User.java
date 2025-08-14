package com.baymotors.domain.user;

public abstract class User {
    private final String id;
    private final String name;
    private final String contact;

    protected User(String id, String name, String contact) {
        this.id = id;
        this.name = name;
        this.contact = contact;
    }

    public String getId() { return id; }
    public String getName() { return name; }
    public String getContact() { return contact; }
}
