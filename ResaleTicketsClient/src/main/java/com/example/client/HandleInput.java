package com.example.client;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class HandleInput {

    public static String signUpValidInput(String password, String name, String phoneNumber) {
        StringBuilder errorInfo = new StringBuilder();
        errorInfo.append(HandleInput.stringInput("Name", name)).append(HandleInput.intInput("Phone Number", phoneNumber));
        if (password.isEmpty()) {
            errorInfo.append(" - Password Is Empty.");
        }

        return errorInfo.toString();
    }

    public static String addingTicketValidInput(String price, String showName, String category, String date,
                                                String section, String row, String seat) {
        StringBuilder errorInfo = new StringBuilder();
        errorInfo.append(HandleInput.doubleInput("Price", price));
        if (showName.isEmpty()) {
            errorInfo.append(" - Show Name Is Empty.\n");
        }
        errorInfo.append(HandleInput.stringInput("Category", category))
                .append(HandleInput.dateInput(date))
                .append(HandleInput.intInput("Section", section))
                .append(HandleInput.intInput("Row", row))
                .append(HandleInput.intInput("Seat", seat));

        return errorInfo.toString();
    }

    public static String emailInput(String emailInput) {
        String EMAIL_PATTERN =
                "^[_A-Za-z0-9-+]+(\\.[_A-Za-z0-9-]+)*@" +
                        "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";
        Pattern pattern = Pattern.compile(EMAIL_PATTERN);
        Matcher matcher = pattern.matcher(emailInput);

        return matcher.matches() ? "" : "Email Not In The Format";
    }

    private static String stringInput(String fieldName, String strInput) {
        String pattern = "^[a-z A-Z]+$";
        return Pattern.matches(pattern, strInput) ? "" : " - " + fieldName + " Should Be With Only Letters.\n";
    }

    public static String doubleInput(String fieldName, String doubleInput) {
        return isValidDouble(doubleInput) ? "" : " - " + fieldName + " Should Be A Number.\n";
    }

    private static String intInput(String fieldName, String intInput) {
        return isValidInteger(intInput) ? "" : " - " + fieldName + " Should Be An Integer Number.\n";
    }

    public static String dateInput(String dateInput) {
        return isValidDate(dateInput) ? "" : " - Date Format Should Be yyyy-MM-dd.\n";
    }

    private static boolean isValidDate(String dateString) {
        String DATE_FORMAT = "yyyy-MM-dd";
        SimpleDateFormat dateFormat = new SimpleDateFormat(DATE_FORMAT);
        dateFormat.setLenient(false);

        try {
            dateFormat.parse(dateString);
            return true;
        } catch (ParseException e) {
            return false;
        }
    }

    private static boolean isValidInteger(String str) {
        try {
            Integer.parseInt(str);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    private static boolean isValidDouble(String str) {
        try {
            Double.parseDouble(str);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }
}