package com.getjavajobs.library.model;

public class Genre {
    private int id;
    private String genreType;
    public int getId() {
        return id;
    }
    public void setId(int inputId){
         id=inputId;        
    }
    public String getGenreType(){
        return genreType;
    }
    
    
    public void setGenreType(String inputGenreType){
        genreType=inputGenreType;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Genre genre = (Genre) o;

        if (genreType != null ? !genreType.equals(genre.genreType) : genre.genreType != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return genreType != null ? genreType.hashCode() : 0;
    }
}
