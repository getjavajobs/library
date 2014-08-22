package com.getjavajobs.library.services.validators;

import com.getjavajobs.library.model.Book;

/**
 * Created by Vlad on 21.08.2014.
 */
public class BookValidator {
    private Book book;

    public BookValidator(Book book){
        this.book=book;
    }

    public boolean validate(){
        if(book.getAuthor()==null | book.getPublisher()==null | book.getName()==null){
            return false;
        }
        AuthorValidator authorValidator=new AuthorValidator(book.getAuthor());
        if(!authorValidator.validate()){
            return false;
        }
        PublisherValidator publisherValidator=new PublisherValidator(book.getPublisher());
        if(!publisherValidator.validate()){
            return false;
        }
        if(book.getPagesNumber()<0 | book.getPrice()<0){
            return false;
        }
        return true;
    }
}
