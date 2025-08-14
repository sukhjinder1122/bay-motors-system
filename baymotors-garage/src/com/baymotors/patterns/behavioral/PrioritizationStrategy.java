package com.baymotors.patterns.behavioral;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Comparator;
import java.util.List;

import com.baymotors.domain.task.Task;

public interface PrioritizationStrategy extends Comparator<Task> {
    default List<Task> sort(Collection<Task> tasks) {
        List<Task> list = new ArrayList<>(tasks);
        list.sort(this);
        return list;
    }
}
