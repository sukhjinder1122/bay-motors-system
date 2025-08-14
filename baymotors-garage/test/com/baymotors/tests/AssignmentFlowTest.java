package com.baymotors.tests;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import com.baymotors.domain.task.Task;
import com.baymotors.domain.task.TaskPriority;
import com.baymotors.domain.task.TaskStatus;
import com.baymotors.service.GarageSystemFacade;

class AssignmentFlowTest {

    @Test
    void assignPicksHighestPriorityFirst() {
        GarageSystemFacade f = new GarageSystemFacade();
        String c = f.customers().addCustomer("Tina", "1", "t@x.com", false);
        String v = f.customers().addVehicleToCustomer(c, "REGX", "ModelX", "Maker");

        f.assignments().createTask(v, "low", TaskPriority.LOW);
        f.assignments().createTask(v, "high", TaskPriority.HIGH);

        Task t = f.assignments().assignNextWaiting("MECH1");
        assertNotNull(t);
        assertEquals(TaskPriority.HIGH, t.getPriority());
        assertEquals(TaskStatus.ASSIGNED, t.getStatus());
        assertEquals("MECH1", t.getAssignedMechanicId());
    }

    @Test
    void mechanicQueueShowsAssignedTasks() {
        GarageSystemFacade f = new GarageSystemFacade();
        String c = f.customers().addCustomer("Leo", "2", "l@x.com", false);
        String v = f.customers().addVehicleToCustomer(c, "REGY", "ModelY", "Maker");

        String tid = f.assignments().createTask(v, "work", TaskPriority.MEDIUM);
        f.assignments().assignNextWaiting("M1");

        assertTrue(f.assignments().tasksForMechanic("M1")
                .stream().anyMatch(x -> x.getId().equals(tid)));
    }
}
