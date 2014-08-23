package com.getjavajobs.library.model;

import java.util.Date;

/**
 * Created by Roman on 23.08.14.
 */
public class Author {

    private int id;
    private String name;
    private String surname;
    private String fatherName;
    private Date birthDate;
    private String birthPlace;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public Date getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(Date birthDate) {
        this.birthDate = birthDate;
    }

    public String getFatherName() {
        return fatherName;
    }

    public void setFatherName(String fatherName) {
        this.fatherName = fatherName;
    }

    public String getBirthPlace() {
        return birthPlace;
    }

    public void setBirthPlace(String birthPlace) {
        this.birthPlace = birthPlace;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Author)) {
            return false;
        }

        Author author = (Author) o;

        if (birthDate != null ? !birthDate.equals(author.birthDate) : author.birthDate != null) {
            return false;
        }
        if (birthPlace != null ? !birthPlace.equals(author.birthPlace) : author.birthPlace != null) {
            return false;
        }
        if (fatherName != null ? !fatherName.equals(author.fatherName) : author.fatherName != null) {
            return false;
        }
        if (name != null ? !name.equals(author.name) : author.name != null) {
            return false;
        }
        if (surname != null ? !surname.equals(author.surname) : author.surname != null) {
            return false;
        }

        return true;
    }

    @Override
    public int hashCode() {
        int result = (name != null) ? name.hashCode() : 0;
        result = 31 * result + (surname != null ? surname.hashCode() : 0);
        result = 31 * result + (fatherName != null ? fatherName.hashCode() : 0);
        result = 31 * result + (birthDate != null ? birthDate.hashCode() : 0);
        result = 31 * result + (birthPlace != null ? birthPlace.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Name: " + name + ";\n");
        sb.append("Surname: " + surname + ";\n");
        sb.append("Fathername: " + fatherName + ";\n");
        sb.append("Birthdate: " + birthDate.toString() + ";\n");
        sb.append("Birthplace: " + birthPlace + ".");
        return sb.toString();
    }
}
