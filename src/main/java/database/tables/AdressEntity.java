package database.tables;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "Adress", schema = "grupo10DB", catalog = "")
@IdClass(AdressEntityPK.class)
public class AdressEntity {
    private int id;
    private int clientId;
    private String street;
    private Integer number;
    private String city;
    private String postalCode;
    private String country;

    @Id
    @Column(name = "ID")
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Id
    @Column(name = "Client_ID")
    public int getClientId() {
        return clientId;
    }

    public void setClientId(int clientId) {
        this.clientId = clientId;
    }

    @Basic
    @Column(name = "street")
    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    @Basic
    @Column(name = "number")
    public Integer getNumber() {
        return number;
    }

    public void setNumber(Integer number) {
        this.number = number;
    }

    @Basic
    @Column(name = "city")
    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    @Basic
    @Column(name = "postal_code")
    public String getPostalCode() {
        return postalCode;
    }

    public void setPostalCode(String postalCode) {
        this.postalCode = postalCode;
    }

    @Basic
    @Column(name = "country")
    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        AdressEntity that = (AdressEntity) o;
        return id == that.id && clientId == that.clientId && Objects.equals(street, that.street) && Objects.equals(number, that.number) && Objects.equals(city, that.city) && Objects.equals(postalCode, that.postalCode) && Objects.equals(country, that.country);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, clientId, street, number, city, postalCode, country);
    }

    @Override
    public String toString() {
        return
                street + " " +
                        number + " " +
                        postalCode + " " +
                        city + ", " +
                        country;
    }
}
