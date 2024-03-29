package database.tables;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "AddressAssociatedStaff", schema = "grupo10DB", catalog = "")
@IdClass(AddressAssociatedStaffEntityPK.class)
public class AddressAssociatedStaffEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(name = "ID", updatable = false, nullable = false)
    private int id;
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Id
    @ManyToOne
    @JoinColumn(name = "AssociatedStaff_DNI")
    private AssociatedStaffEntity AssociatedStaffDNI;
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
    private Boolean isValid;
    @Basic
    @Column(name = "Planta_Puerta_Oficina")
    private String plantaPuertaOficina;

    public AddressAssociatedStaffEntity(AssociatedStaffEntity AssociatedStaffDNI, String street, int number, String city, String postalCode, String country, String plantaPuertaOficina, boolean isValid) {
        this.AssociatedStaffDNI = AssociatedStaffDNI;
        this.street = street;
        this.number = number;
        this.city = city;
        this.postalCode = postalCode;
        this.country = country;
        this.plantaPuertaOficina = plantaPuertaOficina;
        this.isValid = isValid;
    }

    public AddressAssociatedStaffEntity() {

    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public AssociatedStaffEntity getClientId() {
        return AssociatedStaffDNI ;
    }

    public void setClientId(AssociatedStaffEntity clientId) {
        this.AssociatedStaffDNI = clientId;
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


    public Boolean getValid() {
        return isValid;
    }

    public void setValid(Boolean valid) {
        isValid = valid;
    }

    public String getPlantaPuertaOficina() {
        return plantaPuertaOficina;
    }

    public void setPlantaPuertaOficina(String plantaPuertaOficina) {
        this.plantaPuertaOficina = plantaPuertaOficina;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        AddressAssociatedStaffEntity that = (AddressAssociatedStaffEntity) o;
        return id == that.id && AssociatedStaffDNI == that.AssociatedStaffDNI && Objects.equals(street, that.street) && Objects.equals(city, that.city) && Objects.equals(postalCode, that.postalCode) && Objects.equals(country, that.country);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, AssociatedStaffDNI, street, city, postalCode, country);
    }

    @Override
    public String toString() {
        return street + " " +
                postalCode + " " +
                city + ", " +
                country;
    }
}
