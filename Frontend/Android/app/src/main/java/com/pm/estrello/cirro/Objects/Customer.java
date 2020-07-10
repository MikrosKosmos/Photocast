package com.pm.estrello.cirro.Objects;

import androidx.annotation.NonNull;

import com.pm.estrello.cirro.Helpers.Constants;
import com.pm.estrello.cirro.Helpers.Messages;

import org.json.JSONException;
import org.json.JSONObject;

public class Customer {
    private int id;
    private String firstName;
    private String lastName;
    private String email;
    private String phoneNumber;
    private char gender;
    private String statusName;
    private int statusId;
    private String referralCode;
    private String usedReferralCode;

    public Customer(int id, String firstName, String lastName, String email, String phoneNumber, char gender, String statusName, int statusId, String referralCode, String usedReferralCode) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.phoneNumber = phoneNumber;
        this.gender = gender;
        this.statusName = statusName;
        this.statusId = statusId;
        this.referralCode = referralCode;
        this.usedReferralCode = usedReferralCode;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public char getGender() {
        return gender;
    }

    public void setGender(char gender) {
        this.gender = gender;
    }

    public String getStatusName() {
        return statusName;
    }

    public void setStatusName(String statusName) {
        this.statusName = statusName;
    }

    public int getStatusId() {
        return statusId;
    }

    public void setStatusId(int statusId) {
        this.statusId = statusId;
    }

    public String getReferralCode() {
        return referralCode;
    }

    public void setReferralCode(String referralCode) {
        this.referralCode = referralCode;
    }

    public String getUsedReferralCode() {
        return usedReferralCode;
    }

    public void setUsedReferralCode(String usedReferralCode) {
        this.usedReferralCode = usedReferralCode;
    }

    @NonNull
    @Override
    public String toString() {
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put(Constants.FIRST_NAME, this.firstName);
            jsonObject.put(Constants.LAST_NAME, this.lastName);
            jsonObject.put(Constants.EMAIL, this.email);
            jsonObject.put(Constants.GENDER, this.gender);
            jsonObject.put(Constants.PHONE_NUMBER, this.phoneNumber);
            jsonObject.put(Constants.STATUS_NAME, this.statusName);
        } catch (JSONException e) {
            Messages.log(Customer.class.getSimpleName(), e.toString());
        }
        return jsonObject.toString();
    }
}
