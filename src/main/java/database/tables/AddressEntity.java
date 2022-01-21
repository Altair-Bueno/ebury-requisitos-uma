package database.tables;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "Address", schema = "grupo10DB", catalog = "")
@IdClass(AddressEntityPK.class)
public class AddressEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(name = "ID", updatable = false, nullable = false)
    private int id;
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Id
    @ManyToOne
    @JoinColumn(name = "Client_ID")
    private ClientEntity clientId;
    @Basic
    @Column(name = "street")
    private String street;
    @Basic
    @Column(name = "number")
    private int number;
    @Basic
    @Column(name = "city")
    private String city;
    @Basic
    @Column(name = "postal_code")
    private String postalCode;
    @Basic
    @Column(name = "country")
    private String country;
    @Basic
    @Column(name = "isValid")
    private boolean isValid;

    public AddressEntity(ClientEntity clientId, String street, int number, String city, String postalCode, String country, String plantaPuertaOficina, Boolean isValid) {
        //this.id = 0;
        this.clientId = clientId;
        this.street = street;
        this.number = number;
        this.city = city;
        this.postalCode = postalCode;
        this.country = country;
        this.isValid = isValid;
    }

    public AddressEntity() {

    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public ClientEntity getClientId() {
        return clientId;
    }

    public void setClientId(ClientEntity clientId) {
        this.clientId = clientId;
    }

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
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

    public void setValid(boolean valid) { this.isValid = valid; }

    public boolean getValid() { return isValid; }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        AddressEntity that = (AddressEntity) o;
        return id == that.id && clientId == that.clientId && Objects.equals(street, that.street) && Objects.equals(city, that.city) && Objects.equals(postalCode, that.postalCode) && Objects.equals(country, that.country);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, clientId, street, city, postalCode, country);
    }

    @Override
    public String toString() {
        return street + " " +
                postalCode + " " +
                city + ", " +
                country;
    }
}
