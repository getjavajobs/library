package com.getjavajobs.library.models;


import java.util.Date;

public class Borrow {

    private int borrowId = 0;
    private int bookId;
    private int employeeId;
    private int readerId;
    private Date dateOfBorrow;
    private Date dateOfReturn;

    public int getBorrowId() {
        return borrowId;
    }

    public int getBookId() {
        return bookId;
    }

    public int getEmployeeId() {
        return employeeId;
    }

    public int getReaderId() {
        return readerId;
    }

    public Date getDateOfBorrow() {
        return dateOfBorrow;
    }

    public Date getDateOfReturn() {
        return dateOfReturn;
    }


    public void setBorrowId(int borrowId) {

        this.borrowId = borrowId;
    }

    public void setBookId(int bookId) {
        this.bookId = bookId;
    }

    public void setEmployeeId(int employeeId) {
        this.employeeId = employeeId;
    }

    public void setReaderId(int readerId) {
        this.readerId = readerId;
    }

    public void setDateOfBorrow(Date dateOfBorrow) {
        this.dateOfBorrow = dateOfBorrow;
    }

    public void setDateOfReturn(Date dateOfReturn) {
        this.dateOfReturn = dateOfReturn;
    }
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Borrow borrow = (Borrow) o;

        if (bookId != borrow.bookId) return false;
        if (borrowId != borrow.borrowId) return false;
        if (employeeId != borrow.employeeId) return false;
        if (readerId != borrow.readerId) return false;
        if (!dateOfBorrow.equals(borrow.dateOfBorrow)) return false;
        if (!dateOfReturn.equals(borrow.dateOfReturn)) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = borrowId;
        result = 31 * result + bookId;
        result = 31 * result + employeeId;
        result = 31 * result + readerId;
        result = 31 * result + dateOfBorrow.hashCode();
        result = 31 * result + dateOfReturn.hashCode();
        return result;
    }

}
