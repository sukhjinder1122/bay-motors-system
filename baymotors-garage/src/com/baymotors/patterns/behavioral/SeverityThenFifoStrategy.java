package com.baymotors.patterns.behavioral;

import com.baymotors.domain.task.Task;

public class SeverityThenFifoStrategy implements PrioritizationStrategy {
    @Override
    public int compare(Task a, Task b) {
        int p = a.getPriority().ordinal() - b.getPriority().ordinal(); // HIGH(0) first
        if (p != 0) return p;
        return a.getCreatedAt().compareTo(b.getCreatedAt());            // older first
    }
}
