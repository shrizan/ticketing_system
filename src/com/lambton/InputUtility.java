package com.lambton;


import java.util.Scanner;

abstract public class InputUtility {
    public static Scanner scanner = new Scanner(System.in);

    public static double getDoubleInput(String message) {
        while (true) {
            System.out.print(message);
            try {
                scanner = new Scanner(System.in);
                return scanner.nextDouble();
            } catch (Exception ex) {
                System.out.println("Invalid input!!!");
            }
        }
    }

    public static int getInt(String message) {
        while (true) {
            System.out.print(message);
            try {
                return Integer.parseInt(scanner.nextLine());
            } catch (Exception ex) {
                System.out.println("Invalid input!!!");
            }
        }
    }

    public static long getLong(String message) {
        while (true) {
            System.out.print(message);
            try {
                return Long.parseLong(scanner.nextLine());
            } catch (Exception ex) {
                System.out.println("Invalid input!!!");
            }
        }
    }

    public static String getString(String message) {
        while (true) {
            System.out.print(message);
            try {
                return scanner.nextLine();
            } catch (Exception ex) {
                System.out.println("Invalid input!!!");
            }
        }
    }

}
