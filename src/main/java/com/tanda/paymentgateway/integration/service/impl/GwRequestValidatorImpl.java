package com.tanda.paymentgateway.integration.service.impl;


import com.tanda.paymentgateway.integration.model.GwRequest;
import com.tanda.paymentgateway.integration.service.GwRequestValidator;
import org.jetbrains.annotations.NotNull;
import org.springframework.stereotype.Service;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

// Validator Gw request for phone number and Amount

@Service
public class GwRequestValidatorImpl implements GwRequestValidator {
    /*private boolean isValidPhoneNumber(String phoneNumber) {
        String cleanNumber = phoneNumber.replaceAll("\\D", "");
        return cleanNumber.length() >= 11 && cleanNumber.startsWith("254");
    }
    String inputPhoneNumber = ""; //todo: populate correct number String validPhoneNumber = null;
    Pattern pattern = Pattern.compile("^(?:254|\\+254|0)?((?:(?:7(?:(?:[01249][0-9])|(?:5[789])|(?:6[89])))|(?:1(?:[1][0-5])))[0-9]{6})$");
    Matcher matcher = pattern.matcher(inputPhoneNumber);
    if (matcher.matches()) {
        validPhoneNumber = "254" + matcher.group(1);
    }*/

    //Check if it is a valid safaricom number
    private boolean isValidPhoneNumber( String phoneNumber) {
        String inputPhoneNumber = phoneNumber.replaceAll("\\D", ""); // Remove non-digit characters
        String validPhoneNumber = null;

        Pattern pattern = Pattern.compile("^(?:254|\\+254|0)?((?:(?:7(?:(?:[01249][0-9])|(?:5[789])|(?:6[89])))|(?:1(?:[1][0-5])))[0-9]{6})$");
        Matcher matcher = pattern.matcher(inputPhoneNumber);

        if (matcher.matches()) {
            validPhoneNumber = "254" + matcher.group(1);
        }

        return validPhoneNumber != null && validPhoneNumber.length() >= 12;
    }
    private boolean isAmountBetweenRange(Float amount) {
        return amount >= 10 && amount <= 150000;
    }
    public boolean isValidGwRequest( GwRequest gwRequest) {
        return isValidPhoneNumber(gwRequest.getMobileNumber()) && isAmountBetweenRange(gwRequest.getAmount());
    }
}
