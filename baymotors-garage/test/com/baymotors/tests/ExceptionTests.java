package com.baymotors.tests;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import com.baymotors.domain.task.TaskPriority;
import com.baymotors.exceptions.DuplicateEntityException;
import com.baymotors.exceptions.EntityNotFoundException;
import com.baymotors.exceptions.InvalidOperationException;
import com.baymotors.service.GarageSystemFacade;

class ExceptionTests {

    @Test
    void duplicateVehicleThrows() {
        GarageSystemFacade f = new GarageSystemFacade();
        String c = f.customers().addCustomer("Mia", "1", "m@x.com", false);
        f.customers().addVehicleToCustomer(c, "REG9", "Z", "Make");
        assertThrows(DuplicateEntityException.class,
                () -> f.customers().addVehicleToCustomer(c, "REG9", "Z2", "Make"));
    }

    @Test
    void supplierForUnknownManufacturerThrows() {
        GarageSystemFacade f = new GarageSystemFacade();
        assertThrows(EntityNotFoundException.class,
                () -> f.catalog().addSupplier("Unknown", "S", "c"));
    }

    @Test
    void startingTaskWithWrongMechanicThrows() {
        GarageSystemFacade f = new GarageSystemFacade();
        String c = f.customers().addCustomer("Noah", "p", "n@x.com", false);
        String v = f.customers().addVehicleToCustomer(c, "REG88", "A", "M");
        String tid = f.assignments().createTask(v, "x", TaskPriority.HIGH);
        f.assignments().assignNextWaiting("E1");
        assertThrows(InvalidOperationException.class,
                () -> f.assignments().startTask(tid, "E2"));
    }
}
