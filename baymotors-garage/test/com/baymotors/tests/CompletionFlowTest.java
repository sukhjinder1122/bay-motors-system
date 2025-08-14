package com.baymotors.tests;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import com.baymotors.domain.task.Task;
import com.baymotors.domain.task.TaskPriority;
import com.baymotors.domain.task.TaskStatus;
import com.baymotors.service.GarageSystemFacade;

class CompletionFlowTest {

    @Test
    void startThenCompleteChangesStatesAndNotes() {
        GarageSystemFacade f = new GarageSystemFacade();
        String c = f.customers().addCustomer("A", "p", "a@x.com", true);
        String v = f.customers().addVehicleToCustomer(c, "RZ1", "S1", "M1");
        String tid = f.assignments().createTask(v, "diagnose", TaskPriority.MEDIUM);

        Task assigned = f.assignments().assignNextWaiting("E1");
        assertEquals(tid, assigned.getId());

        f.assignments().startTask(tid, "E1");
        assertEquals(TaskStatus.IN_PROGRESS, assigned.getStatus());

        f.assignments().completeTask(tid, "E1", "fixed");
        assertEquals(TaskStatus.DONE, assigned.getStatus());
        assertEquals("fixed", assigned.getCompletionNotes());
    }
}
