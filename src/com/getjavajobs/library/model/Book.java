package com.getjavajobs.library.model;

/**
 * Created by Vlad on 21.08.2014.
 */
//Таблицу «Книги», содержащую информацию о книгах: Название книги, Автор, Год издания, Число страниц, Цена.
public class Book {
    private int id;
    private String name;
    private Author author;
    private Publisher publisher;
    private int year;
    private int pagesNumber;
    private float price;

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public Author getAuthor() {
        return author;
    }

    public Publisher getPublisher() {
        return publisher;
    }

    public int getPagesNumber() {
        return pagesNumber;
    }

    public int getYear() {
        return year;
    }

    public double getPrice() {
        return price;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setAuthor(Author author) {
        this.author = author;
    }

    public void setPublisher(Publisher publisher) {
        this.publisher = publisher;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public void setPagesNumber(int pagesNumber) {
        this.pagesNumber = pagesNumber;
    }

    public void setPrice(float price) {
        this.price = price;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Book book = (Book) o;

        if (author != null ? !author.equals(book.author) : book.author != null) return false;
        if (name != null ? !name.equals(book.name) : book.name != null) return false;
        if (publisher != null ? !publisher.equals(book.publisher) : book.publisher != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = name != null ? name.hashCode() : 0;
        result = 31 * result + (author != null ? author.hashCode() : 0);
        result = 31 * result + (publisher != null ? publisher.hashCode() : 0);
        return result;
    }



}
