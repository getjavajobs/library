package com.getjavajobs.library.model;


import java.util.Date;

public class Borrow {

    private int borrowId = 0;
    private Book book;
    private Date dateOfBorrow;
    private Date dateOfReturn;
    private Employee employee;
    private Reader reader;


    public int getBorrowId() {
        return borrowId;
    }

    public Book getBook() {
        return book;
    }

    public Date getDateOfBorrow() {
        return dateOfBorrow;
    }

    public Date getDateOfReturn() {
        return dateOfReturn;
    }

    public Employee getEmployee() {
        return employee;
    }

    public Reader getReader() {
        return reader;
    }

    public void setBorrowId(int borrowId) {
        this.borrowId = borrowId;
    }

    public void setBook(Book book) {
        this.book = book;
    }

    public void setDateOfBorrow(Date dateOfBorrow) {
        this.dateOfBorrow = dateOfBorrow;
    }

    public void setDateOfReturn(Date dateOfReturn) {
        this.dateOfReturn = dateOfReturn;
    }

    public void setEmployee(Employee employee) {
        this.employee = employee;
    }

    public void setReader(Reader reader) {
        this.reader = reader;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Borrow)) return false;

        Borrow borrow = (Borrow) o;

        if (borrowId != borrow.borrowId) return false;
        if (!book.equals(borrow.book)) return false;
        if (!dateOfBorrow.equals(borrow.dateOfBorrow)) return false;
        if (!dateOfReturn.equals(borrow.dateOfReturn)) return false;
        if (!employee.equals(borrow.employee)) return false;
        if (!reader.equals(borrow.reader)) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = borrowId;
        result = 31 * result + book.hashCode();
        result = 31 * result + dateOfBorrow.hashCode();
        result = 31 * result + dateOfReturn.hashCode();
        result = 31 * result + employee.hashCode();
        result = 31 * result + reader.hashCode();
        return result;
    }
}
