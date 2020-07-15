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
    private String gender;
    private String statusName;
    private int statusId;
    private String referralCode;
    private String usedReferralCode;
    private Address[] addresses;

    public Customer(int id, String firstName, String lastName, String email, String phoneNumber, String gender, String statusName, int statusId, String referralCode, String usedReferralCode) {
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

    public Customer(String firstName, String lastName, String email, String phoneNumber, String gender) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.phoneNumber = phoneNumber;
        this.gender = gender;
    }

    public Customer(JSONObject jsonObject) throws JSONException {
        this.id = jsonObject.getInt(Constants.ID);
        this.firstName = jsonObject.getString(Constants.FIRST_NAME);
        this.lastName = jsonObject.getString(Constants.LAST_NAME);
        this.phoneNumber = jsonObject.getString(Constants.PHONE_NUMBER);
        this.email = jsonObject.getString(Constants.EMAIL);
        this.gender = jsonObject.getString(Constants.GENDER);
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

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
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

    public Address[] getAddresses() {
        return addresses;
    }

    public void setAddresses(Address[] addresses) {
        this.addresses = addresses;
    }

    @NonNull
    @Override
    public String toString() {
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put(Constants.ID, this.id);
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
