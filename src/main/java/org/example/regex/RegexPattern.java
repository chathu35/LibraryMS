package org.example.regex;

import java.util.regex.Pattern;

public class RegexPattern {
    private static final Pattern namePattern = Pattern.compile("^[a-zA-Z '.-]{4,}$");
    private static final Pattern emailPattern = Pattern.compile("(^[a-zA-Z0-9_.-]+)@([a-zA-Z]+)([\\.])([a-zA-Z]+)$");
    private static final Pattern addressPattern = Pattern.compile("^[A-Za-z0-9'\\/\\.\\,]{5,}$");
    private static final Pattern doublePattern = Pattern.compile("^[0-9]+\\.?[0-9]*$");
    private static final Pattern intPattern = Pattern.compile("([0-9])([^A-Za-z]){0,}");

    public static Pattern getNamePattern() {
        return namePattern;
    }

    public static Pattern getEmailPattern() {
        return emailPattern;
    }

    public static Pattern getAddressPattern() {
        return addressPattern;
    }


    public static Pattern getDoublePattern() {
        return doublePattern;
    }

    public static Pattern getIntPattern() {
        return intPattern;
    }
}
