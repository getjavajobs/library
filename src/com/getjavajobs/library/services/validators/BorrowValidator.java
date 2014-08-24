package com.getjavajobs.library.services.validators;

import com.getjavajobs.library.model.Borrow;

/**
 * Created by Глеб on 23.08.2014.
 */
public class BorrowValidator {
    Borrow borrow;
    public BorrowValidator(Borrow borrow){
        this.borrow = borrow;
    }
    public boolean validate(){
        return borrow.getDateOfBorrow().before(borrow.getDateOfReturn());
    }
}
