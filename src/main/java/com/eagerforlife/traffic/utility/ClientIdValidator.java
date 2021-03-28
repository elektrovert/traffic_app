package com.eagerforlife.traffic.utility;

import java.util.regex.Pattern;

public class ClientIdValidator {

    public static final String EMAIL = "email";
    public static final String PHONE = "phone";

    private static final String EMAIL_PATTERN = "^(?=.{1,64}@)[A-Za-z0-9_-]+(\\.[A-Za-z0-9_-]+)*@[^-][A-Za-z0-9-]+(\\.[A-Za-z0-9-]+)*(\\.[A-Za-z]{2,})$";
    private static final Pattern emailPattern = Pattern.compile(EMAIL_PATTERN);

    private static final String PHONE_NUMBER_PATTERN = "^(\\+\\d{1,2}\\s)?\\(?\\d{3}\\)?[\\s.-]\\d{3}[\\s.-]\\d{4}$";
    private static final Pattern phonePattern = Pattern.compile(PHONE_NUMBER_PATTERN);

    /**
     * Matches the following:
     * uppercase and lowercase Latin letters A to Z and a to z
     * digits 0 to 9
     * Allow dot (.), underscore (_) and hyphen (-)
     * dot (.) is not the first or last character
     * dot (.) does not appear consecutively, e.g. my..email@gmail.com is not allowed
     * Max 64 characters
     */
    public boolean validateEmailFormat(String emailAddress) {
        return emailPattern.matcher(emailAddress).matches();
    }

    /**
     * Matches the following:
     * 123-456-7890
     * (123) 456-7890
     * 123 456 7890
     * 123.456.7890
     * +91 (123) 456-7890
     */
    public boolean validatePhoneNumberFormat(String phoneNumber) {
        return phonePattern.matcher(phoneNumber).matches();
    }
}
