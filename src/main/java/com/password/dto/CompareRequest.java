package com.password.dto;

public class CompareRequest {
    private String password1;
    private String password2;
    private String lang;

    public CompareRequest() {
    }

    public CompareRequest(String password1, String password2) {
        this.password1 = password1;
        this.password2 = password2;
    }

    public CompareRequest(String password1, String password2, String lang) {
        this.password1 = password1;
        this.password2 = password2;
        this.lang = lang;
    }

    public String getPassword1() {
        return password1;
    }

    public void setPassword1(String password1) {
        this.password1 = password1;
    }

    public String getPassword2() {
        return password2;
    }

    public void setPassword2(String password2) {
        this.password2 = password2;
    }

    public String getLang() {
        return lang != null ? lang : "ru";
    }

    public void setLang(String lang) {
        this.lang = lang;
    }
}
