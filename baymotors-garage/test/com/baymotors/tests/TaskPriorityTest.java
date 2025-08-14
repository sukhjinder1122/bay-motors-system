package com.baymotors.tests;

import static org.junit.jupiter.api.Assertions.*;

import java.util.List;

import org.junit.jupiter.api.Test;

import com.baymotors.domain.task.Task;
import com.baymotors.domain.task.TaskPriority;
import com.baymotors.repo.TaskRepository;

class TaskPriorityTest {

    @Test
    void waitingTasksAreSortedByPriorityThenFifo() throws Exception {
        TaskRepository repo = new TaskRepository();

        Task a = new Task("A", "V1", "low", TaskPriority.LOW);
        Thread.sleep(2);
        Task b = new Task("B", "V1", "high", TaskPriority.HIGH);
        Thread.sleep(2);
        Task c = new Task("C", "V1", "medium", TaskPriority.MEDIUM);
        Thread.sleep(2);
        Task d = new Task("D", "V1", "high 2", TaskPriority.HIGH);

        repo.save(a);
        repo.save(b);
        repo.save(c);
        repo.save(d);

        List<Task> waiting = repo.waiting();
        assertEquals("B", waiting.get(0).getId()); // HIGH older first
        assertEquals("D", waiting.get(1).getId()); // HIGH newer second
        assertEquals("C", waiting.get(2).getId()); // MEDIUM
        assertEquals("A", waiting.get(3).getId()); // LOW
    }
}
