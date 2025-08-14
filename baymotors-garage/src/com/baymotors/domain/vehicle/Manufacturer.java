package com.baymotors.domain.vehicle;

import java.util.ArrayList;
import java.util.List;

public class Manufacturer {
    private final String name;
    private final List<Supplier> suppliers = new ArrayList<>();

    public Manufacturer(String name) {
        this.name = name;
    }

    public String getName() { return name; }
    public List<Supplier> getSuppliers() { return suppliers; }
    public void addSupplier(Supplier s) { suppliers.add(s); }
}
