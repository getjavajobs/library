package com.getjavajobs.library.services.validators;

import com.getjavajobs.library.model.Author;
import org.springframework.stereotype.Component;

/**
 * Created by Vlad on 24.08.2014.
 */
@Component
public class AuthorValidator {
    private Author author;

    public AuthorValidator(Author author){
        this.author=author;
    }

    public AuthorValidator(){

    }
    public boolean validate(Author author) {
        if (author.getName() == null) return false;
        if (author.getSurname() == null) return false;
        if (author.getPatronymic() == null) return false;
        if (author.getBirthDate() == null) return false;
        if (author.getBirthPlace() == null) return false;
        return true;
    }

    public boolean validate() {
        if (author.getName() == null) return false;
        if (author.getSurname() == null) return false;
        if (author.getPatronymic() == null) return false;
        if (author.getBirthDate() == null) return false;
        if (author.getBirthPlace() == null) return false;
        return true;
    }
}
