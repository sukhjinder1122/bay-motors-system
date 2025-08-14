package com.baymotors.repo;

import java.util.*;

import com.baymotors.domain.vehicle.Manufacturer;

public class ManufacturerRepository {
    private final Map<String, Manufacturer> data = new HashMap<>();

    public boolean exists(String name) { return data.containsKey(name.toLowerCase()); }
    public void save(Manufacturer m) { data.put(m.getName().toLowerCase(), m); }
    public Optional<Manufacturer> find(String name) { return Optional.ofNullable(data.get(name.toLowerCase())); }
    public List<Manufacturer> findAll() { return new ArrayList<>(data.values()); }
}
