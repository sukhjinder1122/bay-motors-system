package com.baymotors.app;

import java.util.Scanner;

import com.baymotors.domain.user.Manager;
import com.baymotors.domain.user.Mechanic;
import com.baymotors.domain.user.Role;
import com.baymotors.patterns.creational.UserFactory;
import com.baymotors.service.GarageSystemFacade;
import com.baymotors.ui.ManagerMenu;
import com.baymotors.ui.MechanicMenu;
import com.baymotors.ui.MenuRouter;
import com.baymotors.ui.InputReader;
import com.baymotors.util.SampleDataSeeder;

public class ConsoleApp {

    private final Scanner scanner = new Scanner(System.in);
    private final InputReader in = new InputReader(scanner);
    private final GarageSystemFacade facade = new GarageSystemFacade();

    public ConsoleApp() {
        new SampleDataSeeder(facade).seed();
    }

    public void start() {
        MenuRouter router = new MenuRouter(in);
        while (true) {
            Role role = router.chooseRole();
            switch (role) {
                case MANAGER -> runManager();
                case MECHANIC -> runMechanic();
                default -> { }
            }
            if (!router.runAgain()) break;
        }
        System.out.println("Goodbye.");
    }

    private void runManager() {
        String name = in.askNonEmpty("Manager name: ");
        String contact = in.askNonEmpty("Contact (email/phone): ");
        Manager mgr = UserFactory.createManager(name, contact);
        new ManagerMenu(in, facade, mgr).show();
    }

    private void runMechanic() {
        String name = in.askNonEmpty("Mechanic name: ");
        String contact = in.askNonEmpty("Contact (email/phone): ");
        Mechanic mech = UserFactory.createMechanic(name, contact);
        new MechanicMenu(in, facade, mech).show();
    }
}
