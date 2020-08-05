package com.pm.estrello.cirro.Objects;

import androidx.annotation.NonNull;

import com.pm.estrello.cirro.Helpers.Constants;
import com.pm.estrello.cirro.Helpers.Messages;

import org.json.JSONException;
import org.json.JSONObject;

public class Vendor {
    private int id;
    private String firstName;
    private String lastName;
    private String email;
    private String phoneNumber;
    private String gender;
    private String statusName;
    private int statusId;
    private String vendorType;
    private String companyBrandName;
    private String address1;
    private String address2;
    private int cityId;
    private String cityName;
    private int pincode;
    private double gpsLat;
    private double gpsLong;
    private String documentType;
    private String documentIdNumber;
    private String profileImageUrl;

    public Vendor(int id, String firstName, String lastName, String email, String phoneNumber, String gender, String statusName, int statusId, String vendorType, String companyBrandName, String address1, String address2, int cityId, String cityName, int pincode, double gpsLat, double gpsLong, String documentType, String documentIdNumber) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.phoneNumber = phoneNumber;
        this.gender = gender;
        this.statusName = statusName;
        this.statusId = statusId;
        this.vendorType = vendorType;
        this.companyBrandName = companyBrandName;
        this.address1 = address1;
        this.address2 = address2;
        this.cityId = cityId;
        this.cityName = cityName;
        this.pincode = pincode;
        this.gpsLat = gpsLat;
        this.gpsLong = gpsLong;
        this.documentType = documentType;
        this.documentIdNumber = documentIdNumber;
    }

    public Vendor(String firstName, String lastName, String email, String phoneNumber, String gender, String statusName, int statusId, String vendorType, String companyBrandName, String address1, String address2, int cityId, String cityName, int pincode, double gpsLat, double gpsLong, String documentType, String documentIdNumber) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.phoneNumber = phoneNumber;
        this.gender = gender;
        this.statusName = statusName;
        this.statusId = statusId;
        this.vendorType = vendorType;
        this.companyBrandName = companyBrandName;
        this.address1 = address1;
        this.address2 = address2;
        this.cityId = cityId;
        this.cityName = cityName;
        this.pincode = pincode;
        this.gpsLat = gpsLat;
        this.gpsLong = gpsLong;
        this.documentType = documentType;
        this.documentIdNumber = documentIdNumber;
    }

    public Vendor(JSONObject jsonObject) throws JSONException {
        this.id = jsonObject.getInt(Constants.ID);
        this.firstName = jsonObject.getString(Constants.FIRST_NAME);
        this.lastName = jsonObject.getString(Constants.LAST_NAME);
        this.phoneNumber = jsonObject.getString(Constants.PHONE_NUMBER);
        this.email = jsonObject.getString(Constants.EMAIL_ID);
        this.gender = jsonObject.getString(Constants.GENDER);
        this.vendorType = jsonObject.getString(Constants.VENDOR_TYPE);
        this.companyBrandName = jsonObject.getString(Constants.COMPANY_BRAND_NAME);
        this.documentType = jsonObject.getString(Constants.DOCUMENT_TYPE);
        this.documentIdNumber = jsonObject.getString(Constants.DOCUMENT_ID_NUMBER);
        this.address1 = jsonObject.getString(Constants.ADDRESS_1);
        this.address2 = jsonObject.getString(Constants.ADDRESS_2);
        this.pincode = jsonObject.getInt(Constants.PINCODE);
        this.cityId = jsonObject.getInt(Constants.CITY);
        this.gpsLat = jsonObject.getDouble(Constants.GPS_LAT);
        this.gpsLong = jsonObject.getDouble(Constants.GPS_LONG);
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

    public String getVendorType() {
        return vendorType;
    }

    public void setVendorType(String vendorType) {
        this.vendorType = vendorType;
    }

    public String getCompanyBrandName() {
        return companyBrandName;
    }

    public void setCompanyBrandName(String companyBrandName) {
        this.companyBrandName = companyBrandName;
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

    public int getCityId() {
        return cityId;
    }

    public void setCityId(int cityId) {
        this.cityId = cityId;
    }

    public String getCityName() {
        return cityName;
    }

    public void setCityName(String cityName) {
        this.cityName = cityName;
    }

    public int getPincode() {
        return pincode;
    }

    public void setPincode(int pincode) {
        this.pincode = pincode;
    }

    public double getGpsLat() {
        return gpsLat;
    }

    public void setGpsLat(double gpsLat) {
        this.gpsLat = gpsLat;
    }

    public double getGpsLong() {
        return gpsLong;
    }

    public void setGpsLong(double gpsLong) {
        this.gpsLong = gpsLong;
    }

    public String getProfileImageUrl() {
        return profileImageUrl;
    }

    public void setProfileImageUrl(String profileImageUrl) {
        this.profileImageUrl = profileImageUrl;
    }

    public String getDocumentType() {
        return documentType;
    }

    public void setDocumentType(String documentType) {
        this.documentType = documentType;
    }

    public String getDocumentIdNumber() {
        return documentIdNumber;
    }

    public void setDocumentIdNumber(String documentIdNumber) {
        this.documentIdNumber = documentIdNumber;
    }

    @NonNull
    @Override
    public String toString() {
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put(Constants.ID, getId());
            jsonObject.put(Constants.FIRST_NAME, getFirstName());
            jsonObject.put(Constants.LAST_NAME, getLastName());
            jsonObject.put(Constants.GENDER, getGender());
            jsonObject.put(Constants.PHONE_NUMBER, getPhoneNumber());
            jsonObject.put(Constants.EMAIL_ID, getEmail());
            jsonObject.put(Constants.VENDOR_TYPE, getVendorType());
            jsonObject.put(Constants.COMPANY_BRAND_NAME, getCompanyBrandName());
            jsonObject.put(Constants.DOCUMENT_TYPE, getDocumentType());
            jsonObject.put(Constants.DOCUMENT_ID_NUMBER, getDocumentIdNumber());
            jsonObject.put(Constants.ADDRESS_1, getAddress1());
            jsonObject.put(Constants.ADDRESS_2, getAddress2());
            jsonObject.put(Constants.PINCODE, getPincode());
            jsonObject.put(Constants.CITY, getCityId());
            jsonObject.put(Constants.GPS_LAT, getGpsLat());
            jsonObject.put(Constants.GPS_LONG, getGpsLong());
        } catch (Exception e) {
            Messages.log(Vendor.class.getSimpleName(), e.toString());
        }
        return jsonObject.toString();
    }
}
