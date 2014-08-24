/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.getjavajobs.library.services.validators;

import com.getjavajobs.library.model.Reader;

/**
 *
 * @author Виталий
 */
public class ReaderValidator {

    private Reader r;

    public ReaderValidator(Reader r) {
        this.r = r;
    }

    public ReaderValidator() {
    }

    public boolean readerValidate() {
         return readerNameValidate() && readerSurNameValidate() && readerAddressValidate() && readerPassportValidate() && readerPhoneValidate();
    }

    private boolean readerNameValidate() {
        String s = r.getFirstName();
        String expr = "[a-zA-Z]+";
        return s.matches(expr);
    }

    private boolean readerSurNameValidate() {
        String s = r.getSecondName();
        String expr = "[a-zA-Z]+";
        return s.matches(expr);
    }

    private boolean readerAddressValidate() {
        String s = r.getAddress();
        String expr = "[a-zA-Z0-9]+";
        return s.matches(expr);
    }

    private boolean readerPhoneValidate() {
        String s = String.valueOf(r.getPhone());
        String expr = "[0-9]{11}";
        return s.matches(expr);
    }

    private boolean readerPassportValidate() {
        String s = r.getPassport();
        String expr = "[a-zA-Z0-9]{5,11}";
        return s.matches(expr);
    }

    public boolean readerValidate(Reader r) {
        return readerNameValidate(r) && readerSurNameValidate(r) && readerAddressValidate(r) && readerPassportValidate(r) && readerPhoneValidate(r);
    }

    private boolean readerNameValidate(Reader r) {
        String s = r.getFirstName();
        String expr = "[a-zA-Z]+";
        return s.matches(expr);
    }

    private boolean readerSurNameValidate(Reader r) {
        String s = r.getSecondName();
        String expr = "[a-zA-Z]+";
        return s.matches(expr);
    }

    private boolean readerAddressValidate(Reader r) {
        String s = r.getAddress();
        String expr = "[a-zA-Z0-9]+";
        return s.matches(expr);
    }

    private boolean readerPhoneValidate(Reader r) {
        String s = String.valueOf(r.getPhone());
        String expr = "[0-9]{11}";
        return s.matches(expr);
    }

    private boolean readerPassportValidate(Reader r) {
        String s = r.getPassport();
        String expr = "[a-zA-Z0-9]{5,10}";
        return s.matches(expr);
    }

}
