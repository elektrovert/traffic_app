package com.eagerforlife.traffic.controller.registration;

public class DeleteRegistrationRequest {

    /**
     * The id of the client to be deleted.
     * Must be either an email address or phone number
     * Email format:
     * uppercase and lowercase Latin letters A to Z and a to z
     * digits 0 to 9
     * Allow dot (.), underscore (_) and hyphen (-)
     * dot (.) is not the first or last character
     * dot (.) does not appear consecutively, e.g. my..email@gmail.com is not allowed
     * Max 64 characters
     * <p>
     * Phone Number format:
     * 123-456-7890
     * (123) 456-7890
     * 123 456 7890
     * 123.456.7890
     * +91 (123) 456-7890
     */
    private String id;

    public String getId() {
        return id;
    }
}
