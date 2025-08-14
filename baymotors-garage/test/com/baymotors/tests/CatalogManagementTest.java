package com.baymotors.tests;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import com.baymotors.domain.vehicle.Manufacturer;
import com.baymotors.exceptions.DuplicateEntityException;
import com.baymotors.service.GarageSystemFacade;

class CatalogManagementTest {

    @Test
    void addManufacturerAndSupplier() {
        GarageSystemFacade f = new GarageSystemFacade();
        f.catalog().addManufacturer("Nissan");
        f.catalog().addSupplier("Nissan", "PartsCo", "parts@nissan.com");

        Manufacturer m = f.catalog().repo().find("Nissan").orElseThrow();
        assertEquals(1, m.getSuppliers().size());
        assertEquals("PartsCo", m.getSuppliers().get(0).getName());
    }

    @Test
    void addingDuplicateManufacturerFails() {
        GarageSystemFacade f = new GarageSystemFacade();
        f.catalog().addManufacturer("Honda");
        assertThrows(DuplicateEntityException.class,
                () -> f.catalog().addManufacturer("Honda"));
    }
}
