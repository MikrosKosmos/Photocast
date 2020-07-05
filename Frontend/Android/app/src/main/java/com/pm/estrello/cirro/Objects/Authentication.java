package com.pm.estrello.cirro.Objects;

public class Authentication {
    private String phoneNumber;
    private int otp;
    private String jwToken;

    public Authentication(String phoneNumber, int otp, String jwToken) {
        this.phoneNumber = phoneNumber;
        this.otp = otp;
        this.jwToken = jwToken;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public int getOtp() {
        return otp;
    }

    public void setOtp(int otp) {
        this.otp = otp;
    }

    public String getJwToken() {
        return jwToken;
    }

    public void setJwToken(String jwToken) {
        this.jwToken = jwToken;
    }
}
