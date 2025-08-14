package com.baymotors.app;

import java.lang.reflect.Method;

public final class Main {

    private Main() {
        // no instances
    }

    public static void main(String[] args) {
        printBanner();

        
        try {
            Class<?> appClass = Class.forName("com.baymotors.app.ConsoleApp");
            Object app = appClass.getDeclaredConstructor().newInstance();
            Method start = appClass.getMethod("start");
            start.invoke(app);
        } catch (ClassNotFoundException e) {
            System.out.println("Console module not found yet.");
            System.out.println("Add com.baymotors.app.ConsoleApp and run again.");
        } catch (ReflectiveOperationException e) {
            System.out.println("Unable to start the console application.");
            System.out.println(e.getClass().getSimpleName() + ": " + e.getMessage());
        }
    }

    private static void printBanner() {
        System.out.println("Bay Motors Garage");
        System.out.println("-----------------");
    }
}
