package com.baymotors.tests;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import com.baymotors.domain.customer.Customer;
import com.baymotors.service.GarageSystemFacade;

class CustomerUpgradeTest {

    @Test
    void upgradeFlagTurnsTrue() {
        GarageSystemFacade f = new GarageSystemFacade();
        String id = f.customers().addCustomer("Jay", "9", "j@x.com", false);
        Customer c = f.customers().get(id);
        assertFalse(c.isRegistered());

        f.customers().upgradeToRegistered(id);
        assertTrue(c.isRegistered());
    }
}
