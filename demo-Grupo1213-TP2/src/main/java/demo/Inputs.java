package demo;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

public class Inputs {

    /**
     * The demo.Inputs errorMessage variable
     */
    private static final String errorMessage = "Sorry, please enter valid value";

    /**
     * Reads an Integer from the buffer
     * @param message The message to be written when reading the data
     * @return The integer that was read
     */
    public static int readInt(String message, int min, int max) {
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        int value = min-1;
        boolean error = true;

        do {
            try {
                System.out.print("\n" + message);
                value = Integer.parseInt(reader.readLine());
                error = false;
            } catch (IOException | NumberFormatException e) {
                System.out.print(errorMessage);
            }
        } while (error || value<min || value>max);

        return value;
    }

    /**
     * Reads a String from the buffer
     * @param message The message to be written when reading the data
     * @return The String that was read
     */
    public static String readString(String message) {
        BufferedReader reader;
        try {
            reader = new BufferedReader(new InputStreamReader(System.in, "UTF-8"));
        } catch (Exception e) {
            reader = new BufferedReader(new InputStreamReader(System.in));
            System.err.println("Ola");
        }
        String value = "";
        boolean error = true;

        do {
            try {
                System.out.print("\n" + message);
                value = reader.readLine();
                error = false;
            } catch (IOException e) {
                System.out.print(errorMessage);
            }
        } while (error);

        return value;
    }

    /**
     * Reads a double from the buffer
     * @param message The message to be written when reading the data
     * @return The double that was read
     */
    public static double readDouble(String message) {
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        double value = 0;
        boolean error = true;

        do {
            try {
                System.out.print("\n" + message);
                value = Double.parseDouble(reader.readLine());
                error = false;
            } catch (IOException | NumberFormatException e) {
                System.out.print(errorMessage);
            }
        } while (error);

        return value;
    }

    /**
     * Prints a string and "pauses the execution" until the user clicks in the enter key
     */
    public static void pause() {
        readString("Press the enter key to continue . . . ");
    }

    /**
     * Reads a string from the buffer and converts it to a date
     * @param message The message to be written when reading the data
     * @return The date that was read
     */
    public static LocalDate readDate(String message) {
        LocalDate dateTime = null;
        String date;
        boolean error = true;

        do {
            date = readString(message+"(yyyy-MM-dd):");

            final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
            try {
                dateTime = LocalDate.parse(date, formatter);
                error=false;
            }catch (DateTimeParseException e){
            }

        } while (error);

        return dateTime;
    }

}
