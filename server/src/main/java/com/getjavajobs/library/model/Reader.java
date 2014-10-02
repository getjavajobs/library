/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.getjavajobs.library.model;

public class Reader {

    private String secondName;
    private String firstName;
    private String address;
    private String passport;
    private String phone;
    private int ReaderId;

    public Reader() {
    }

    public Reader(String secondName, String firstName, String address, String passport, String phone) {
        this.secondName = secondName;
        this.firstName = firstName;
        this.address = address;
        this.passport = passport;
        this.phone = phone;
    }

    public Reader(String secondName, String firstName, String address, String passport, String phone, int ReaderId) {
        this.secondName = secondName;
        this.firstName = firstName;
        this.address = address;
        this.passport = passport;
        this.phone = phone;
        this.ReaderId = ReaderId;
    }

    
    public void setAddress(String address) {
        this.address = address;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public void setPasport(String passport) {
        this.passport = passport;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public void setReaderId(int ReaderId) {
        this.ReaderId = ReaderId;
    }

    public void setSecondName(String secondName) {
        this.secondName = secondName;
    }

    public String getAddress() {
        return address;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getPassport() {
        return passport;
    }

    public String getPhone() {
        return phone;
    }

    public int getReaderId() {
        return ReaderId;
    }

    public String getSecondName() {
        return secondName;
    }

    public void setPassport(String passport) {
        this.passport = passport;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (this.getClass() != obj.getClass()) {
            return false;
        }
        Reader r = (Reader) obj;
        if (hashCode() != r.hashCode()) {
            return false;
        }
        if (((Reader) obj).getReaderId() == ReaderId) {
            return true;
        }
        if (this.getReaderId() == 0){
            return false;
        }
        return true;
    }

    @Override
    public int hashCode() {
        return ReaderId;
    }

}
