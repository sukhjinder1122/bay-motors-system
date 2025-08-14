package com.baymotors.util;

import com.baymotors.domain.task.TaskPriority;
import com.baymotors.service.GarageSystemFacade;

public class SampleDataSeeder {
    private final GarageSystemFacade f;

    public SampleDataSeeder(GarageSystemFacade f) {
        this.f = f;
    }

    public void seed() {
        String c1 = f.customers().addCustomer("Alice", "111-222", "alice@example.com", true);
        String c2 = f.customers().addCustomer("Bob", "333-444", "bob@example.com", false);

        f.catalog().addManufacturer("Toyota");
        f.catalog().addManufacturer("Ford");

        String v1 = f.customers().addVehicleToCustomer(c1, "KA01AB1234", "Corolla", "Toyota");
        String v2 = f.customers().addVehicleToCustomer(c2, "KA02CD5678", "Fiesta", "Ford");

        f.assignments().createTask(v1, "Brake pads replace", TaskPriority.HIGH);
        f.assignments().createTask(v2, "Oil change", TaskPriority.MEDIUM);
    }
}
