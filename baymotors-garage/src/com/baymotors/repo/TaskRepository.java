package com.baymotors.repo;

import java.util.*;
import java.util.stream.Collectors;

import com.baymotors.domain.task.Task;
import com.baymotors.domain.task.TaskStatus;

public class TaskRepository implements Repository<Task, String> {

    private final Map<String, Task> data = new HashMap<>();

    @Override
    public Task save(Task t) {
        data.put(t.getId(), t);
        return t;
    }

    @Override
    public Optional<Task> findById(String id) {
        return Optional.ofNullable(data.get(id));
    }

    @Override
    public boolean existsById(String id) {
        return data.containsKey(id);
    }

    @Override
    public List<Task> findAll() {
        return new ArrayList<>(data.values());
    }

    @Override
    public void deleteById(String id) {
        data.remove(id);
    }

    public List<Task> waiting() {
        return data.values().stream()
                .filter(t -> t.getStatus() == TaskStatus.NEW)
                .sorted(TaskRepository::compare)
                .collect(Collectors.toList());
    }

    public List<Task> forMechanic(String mechId) {
        return data.values().stream()
                .filter(t -> mechId.equals(t.getAssignedMechanicId()))
                .sorted(TaskRepository::compare)
                .collect(Collectors.toList());
    }

    private static int compare(Task a, Task b) {
        int p = a.getPriority().ordinal() - b.getPriority().ordinal();
        if (p != 0) return p; // HIGH(0) before MEDIUM(1) before LOW(2)
        return a.getCreatedAt().compareTo(b.getCreatedAt());
    }
}
