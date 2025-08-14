package com.baymotors.ui;

import com.baymotors.domain.user.Role;

public class MenuRouter {
    private final InputReader in;

    public MenuRouter(InputReader in) {
        this.in = in;
    }

    public Role chooseRole() {
        System.out.println();
        System.out.println("1) Manager");
        System.out.println("2) Mechanic");
        while (true) {
            int pick = in.askInt("Pick role (1-2): ");
            if (pick == 1) return Role.MANAGER;
            if (pick == 2) return Role.MECHANIC;
            System.out.println("Choose 1 or 2.");
        }
    }

    public boolean runAgain() {
        String s = in.ask("Run again? (y/n): ");
        return s.trim().equalsIgnoreCase("y");
    }
}
