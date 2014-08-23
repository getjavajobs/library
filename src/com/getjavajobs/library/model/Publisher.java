package com.getjavajobs.library.model;

/**
 * Created by Roman on 20.08.14.
 */
public class Publisher {

    private int id;
    private String name;
    private String city;
    private String phoneNumber;
    private String email;
    private String siteAddress;

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

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getSiteAddress() {
        return siteAddress;
    }

    public void setSiteAddress(String siteAddress) {
        this.siteAddress = siteAddress;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if ((o == null) || !(o instanceof Publisher)) {
            return false;
        }

        Publisher publisher = (Publisher) o;

        if ((city != null) ? !city.equals(publisher.city) : (publisher.city != null)) {
            return false;
        }
        if ((name != null) ? !name.equals(publisher.name) : (publisher.name != null)) {
            return false;
        }

        return true;
    }

    @Override
    public int hashCode() {
        int result = name != null ? name.hashCode() : 0;
        result = 31 * result + (city != null ? city.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Name: " + name + ";\n");
        sb.append("City: " + city + ";\n");
        sb.append("PhoneNumber: " + phoneNumber + ";\n");
        sb.append("Email: " + email + ";\n");
        sb.append("SiteAdress: " + siteAddress + ".");
        return sb.toString();
    }
}
