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
    
}
