package com.pm.estrello.cirro.Objects;

public class Vendor {
    private int id;
    private String firstName;
    private String lastName;
    private String email;
    private String phoneNumber;
    private char gender;
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
    private String profileImageUrl;

    public Vendor(int id, String firstName, String lastName, String email, String phoneNumber, char gender, String statusName, int statusId, String vendorType, String companyBrandName, String address1, String address2, int cityId, String cityName, int pincode, double gpsLat, double gpsLong, String profileImageUrl) {
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
        this.profileImageUrl = profileImageUrl;
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
}
