package com.getjavajobs.library.services.validators;

import com.getjavajobs.library.model.Genre;

public class GenreValidator {

    private Genre genre;

    public GenreValidator(Genre genre) {
        this.genre = genre;
    }

    public boolean validate() {
        if (genre.getGenreType() == null) {
            return false;
        }
        return true;
    }
}
