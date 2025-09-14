package app.adapter.in.util;
import java.sql.Date;
import java.util.Scanner;



public class InputReader {
    private static Scanner scanner = new Scanner(System.in);

    public static String readString(String prompt) {
        System.out.print(prompt);
        return scanner.nextLine();
    }

    public static Long readLong(String prompt) {
        while (true) {
            try {
                System.out.print(prompt);
                String input = scanner.nextLine();
                return Long.parseLong(input);
            } catch (NumberFormatException e) {
                System.out.println("Ingrese un número válido.");
            }
        }
    }

    public static Integer readInt(String prompt) {
        while (true) {
            try {
                System.out.print(prompt);
                String input = scanner.nextLine();
                return Integer.parseInt(input);
            } catch (NumberFormatException e) {
                System.out.println("Ingrese un número entero válido.");
            }
        }
    }

    public static Double readDouble(String prompt) {
        while (true) {
            try {
                System.out.print(prompt);
                String input = scanner.nextLine();
                return Double.parseDouble(input);
            } catch (NumberFormatException e) {
                System.out.println("ingrese un número decimal válido.");
            }
        }
    }

    public static Date readDate(String prompt) {
        while (true) {
            try {
                System.out.print(prompt);
                String dateString = scanner.nextLine();
                return Date.valueOf(dateString);
            } catch (IllegalArgumentException e) {
                System.out.println("Formato de fecha inválido. Use AAAA-MM-DD.");
            }
        }
    }

    public static boolean readBoolean(String prompt) {
        while (true) {
            String input = readString(prompt + " (s/n): ").toLowerCase();
            if (input.equals("s")) {
                return true;
            } else if (input.equals("n")) {
                return false;
            } else {
                System.out.println("Entrada inválida. Por favor, ingrese 's' o 'n'.");
            }
        }
    }
}
