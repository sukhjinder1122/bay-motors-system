package com.baymotors.repo;

import java.util.*;

import com.baymotors.domain.vehicle.Vehicle;

public class VehicleRepository implements Repository<Vehicle, String> {
    private final Map<String, Vehicle> data = new HashMap<>();

    @Override
    public Vehicle save(Vehicle t) {
        data.put(t.getId(), t);
        return t;
    }

    @Override
    public Optional<Vehicle> findById(String id) {
        return Optional.ofNullable(data.get(id));
    }

    @Override
    public boolean existsById(String id) {
        return data.containsKey(id);
    }

    @Override
    public List<Vehicle> findAll() {
        return new ArrayList<>(data.values());
    }

    @Override
    public void deleteById(String id) {
        data.remove(id);
    }
}
