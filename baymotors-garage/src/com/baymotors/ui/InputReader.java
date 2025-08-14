package com.baymotors.ui;

import java.util.Locale;
import java.util.Scanner;

public class InputReader {
    private final Scanner sc;

    public InputReader(Scanner sc) {
        this.sc = sc;
    }

    public String ask(String prompt) {
        System.out.print(prompt);
        return sc.nextLine();
    }

    public String askNonEmpty(String prompt) {
        while (true) {
            String s = ask(prompt);
            if (s != null && !s.trim().isEmpty()) return s.trim();
            System.out.println("Please enter a value.");
        }
    }

    public int askInt(String prompt) {
        while (true) {
            String s = ask(prompt);
            try {
                return Integer.parseInt(s.trim());
            } catch (NumberFormatException e) {
                System.out.println("Enter a number.");
            }
        }
    }

    public <E extends Enum<E>> E askEnum(String prompt, Class<E> type) {
        while (true) {
            System.out.print(prompt);
            String s = sc.nextLine().trim().toUpperCase(Locale.ROOT);
            try {
                return Enum.valueOf(type, s);
            } catch (IllegalArgumentException ex) {
                System.out.print("Options: ");
                E[] values = type.getEnumConstants();
                for (int i = 0; i < values.length; i++) {
                    System.out.print(values[i].name());
                    if (i < values.length - 1) System.out.print(", ");
                }
                System.out.println();
            }
        }
    }
}
