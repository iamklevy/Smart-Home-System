package com.example.smarthomesystem;

public class Users {

    String user,pass,mail,phone,birthdate,country,city;

    public Users(){

    }

    public Users(String user, String pass, String mail, String phone, String birthdate, String country, String city) {
        this.user = user;
        this.pass = pass;
        this.mail = mail;
        this.phone = phone;
        this.birthdate = birthdate;
        this.country = country;
        this.city = city;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public String getPass() {
        return pass;
    }

    public void setPass(String pass) {
        this.pass = pass;
    }

    public String getMail() {
        return mail;
    }

    public void setMail(String mail) {
        this.mail = mail;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getBirthdate() {
        return birthdate;
    }

    public void setBirthdate(String birthdate) {
        this.birthdate = birthdate;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }


}
