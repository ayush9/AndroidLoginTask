package com.example.androidtestapp;

import java.util.regex.Pattern;

public class EmailValidation {

    public static final String USERNAME_REGEX = "^((?![@:\\/]).)*$";
    public static final String Regex = "^((?!^\\.)(?!.*\\.{2})([\\w!#$%&'*+\\-\\/=?\\^`.{|}~]*)[^.\\s<>(),:;\"@\\[\\]])@[a-zA-Z0-9-]+(\\.[a-zA-Z0-9-]+)+$";
    public static final int PASSWORD_CHARCTER_LIMIT = 5;
    public static final String PASSWORD_NULL_SPACE = " ";
    /**
     * Returns true if valid email and false if invalid email format
     *
     * @param email EmailAddress Of the user
     * @return returns boolean after matching with regex
     */
    public static boolean isValidEmail(String email) {

        return Pattern.compile(Regex).matcher(email).matches();

    }

    /**
     * Returns true if valid email and false if invalid email format
     *
     * @param userName EmailAddress Of the user
     * @return returns boolean after matching with regex
     */
    public static boolean isValidUserName(String userName) {

        return Pattern.compile(USERNAME_REGEX).matcher(userName).matches();
    }

}
