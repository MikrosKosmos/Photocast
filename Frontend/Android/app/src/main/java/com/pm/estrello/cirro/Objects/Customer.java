package com.pm.estrello.cirro.Objects;

public class Customer {
    private int id;
    private String firstName;
    private String lastName;
    private String email;
    private String phoneNumber;
    private char gender;
    private String statusName;
    private int statusId;

    public Customer(int id, String firstName, String lastName, String email, String phoneNumber, char gender, String statusName, int statusId) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.phoneNumber = phoneNumber;
        this.gender = gender;
        this.statusName = statusName;
        this.statusId = statusId;
    }

    public int getId() {
        return id;
    }

    public Customer setId(int id) {
        this.id = id;
        return this;
    }

    public String getFirstName() {
        return firstName;
    }

    public Customer setFirstName(String firstName) {
        this.firstName = firstName;
        return this;
    }

    public String getLastName() {
        return lastName;
    }

    public Customer setLastName(String lastName) {
        this.lastName = lastName;
        return this;
    }

    public String getEmail() {
        return email;
    }

    public Customer setEmail(String email) {
        this.email = email;
        return this;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public Customer setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
        return this;
    }

    public char getGender() {
        return gender;
    }

    public Customer setGender(char gender) {
        this.gender = gender;
        return this;
    }

    public String getStatusName() {
        return statusName;
    }

    public Customer setStatusName(String statusName) {
        this.statusName = statusName;
        return this;
    }

    public int getStatusId() {
        return statusId;
    }

    public Customer setStatusId(int statusId) {
        this.statusId = statusId;
        return this;
    }
}
