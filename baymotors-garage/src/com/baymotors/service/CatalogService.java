package com.baymotors.service;

import com.baymotors.domain.vehicle.Manufacturer;
import com.baymotors.domain.vehicle.Supplier;
import com.baymotors.exceptions.DuplicateEntityException;
import com.baymotors.exceptions.EntityNotFoundException;
import com.baymotors.repo.ManufacturerRepository;

public class CatalogService {
    private final ManufacturerRepository manufacturers;

    public CatalogService(ManufacturerRepository manufacturers) {
        this.manufacturers = manufacturers;
    }

    public void addManufacturer(String name) {
        if (manufacturers.exists(name)) throw new DuplicateEntityException("Manufacturer exists: " + name);
        manufacturers.save(new Manufacturer(name));
    }

    public void addSupplier(String mfrName, String supplierName, String contact) {
        Manufacturer m = manufacturers.find(mfrName)
                .orElseThrow(() -> new EntityNotFoundException("Manufacturer not found: " + mfrName));
        m.addSupplier(new Supplier(supplierName, contact));
    }

    public ManufacturerRepository repo() { return manufacturers; }
}
