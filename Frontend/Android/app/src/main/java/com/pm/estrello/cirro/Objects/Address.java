package com.pm.estrello.cirro.Objects;

import androidx.annotation.NonNull;

import com.pm.estrello.cirro.Helpers.Constants;
import com.pm.estrello.cirro.Helpers.Messages;

import org.json.JSONObject;

public class Address {
    private int addressId;
    private String address1, address2;
    private int pincode;
    private int cityId;
    private int customerId;
    private double latitude;
    private double longitude;

    public Address(int addressId, String address1, String address2, int pincode, int cityId, int customerId, double latitude, double longitude) {
        this.addressId = addressId;
        this.address1 = address1;
        this.address2 = address2;
        this.pincode = pincode;
        this.cityId = cityId;
        this.customerId = customerId;
        this.latitude = latitude;
        this.longitude = longitude;
    }

    public Address(String address1, String address2, int pincode, int cityId, int customerId, double latitude, double longitude) {
        this.address1 = address1;
        this.address2 = address2;
        this.pincode = pincode;
        this.cityId = cityId;
        this.customerId = customerId;
        this.latitude = latitude;
        this.longitude = longitude;
    }

    public int getAddressId() {
        return addressId;
    }

    public void setAddressId(int addressId) {
        this.addressId = addressId;
    }

    public String getAddress1() {
        return address1;
    }

    public void setAddress1(String address1) {
        this.address1 = address1;
    }

    public String getAddress2() {
        return address2;
    }

    public void setAddress2(String address2) {
        this.address2 = address2;
    }

    public int getPincode() {
        return pincode;
    }

    public void setPincode(int pincode) {
        this.pincode = pincode;
    }

    public int getCityId() {
        return cityId;
    }

    public void setCityId(int cityId) {
        this.cityId = cityId;
    }

    public int getCustomerId() {
        return customerId;
    }

    public void setCustomerId(int customerId) {
        this.customerId = customerId;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    @NonNull
    @Override
    public String toString() {
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put(Constants.ADDRESS_1, getAddress1());
            jsonObject.put(Constants.ADDRESS_2, getAddress2());
            jsonObject.put(Constants.CITY, getCityId());
            jsonObject.put(Constants.PINCODE, getPincode());
            jsonObject.put(Constants.GPS_LAT, getLatitude());
            jsonObject.put(Constants.GPS_LONG, getLongitude());
        } catch (Exception e) {
            Messages.log(Address.class.getSimpleName(), e.toString());
        }
        return jsonObject.toString();
    }
}
