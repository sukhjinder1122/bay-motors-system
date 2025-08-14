package com.baymotors.patterns.creational;

import com.baymotors.domain.user.Manager;
import com.baymotors.domain.user.Mechanic;
import com.baymotors.util.IdGenerator;

public final class UserFactory {
    private UserFactory() {}

    public static Manager createManager(String name, String contact) {
        return new Manager(IdGenerator.next("M"), name, contact);
    }

    public static Mechanic createMechanic(String name, String contact) {
        return new Mechanic(IdGenerator.next("E"), name, contact);
    }
}
