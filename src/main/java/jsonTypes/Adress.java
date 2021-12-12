package jsonTypes;

import java.io.Serializable;

public class Adress implements Serializable {

    String city;
    String street;
    String number;
    String postalCode;
    String country;

    public Adress(String city, String street, String number, String postalCode, String country) {
        this.city = city;
        this.street = street;
        this.number = number;
        this.postalCode = postalCode;
        this.country = country;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public String getPostalCode() {
        return postalCode;
    }

    public void setPostalCode(String postalCode) {
        this.postalCode = postalCode;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }
}
