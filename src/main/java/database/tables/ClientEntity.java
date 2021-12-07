package database.tables;

import javax.persistence.*;
import java.sql.Date;
import java.util.Objects;

@Entity
@Table(name = "Client", schema = "grupo10DB", catalog = "")
public class ClientEntity {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "ID")
    private int id;
    @Basic
    @Column(name = "status")
    private String status;
    @Basic
    @Column(name = "NIF")
    private String nif;
    @Basic
    @Column(name = "name")
    private String name;
    @Basic
    @Column(name = "last_name1")
    private String lastName1;
    @Basic
    @Column(name = "last_name2")
    private String lastName2;
    @Basic
    @Column(name = "birth_date")
    private Date birthDate;
    @Basic
    @Column(name = "register_date")
    private Date registerDate;
    @Basic
    @Column(name = "end_date")
    private Date endDate;
    @ManyToOne
    @JoinColumn(name = "address_id", insertable = false, updatable = false)
    @JoinColumn(name = "ID", insertable = false, updatable = false)
    private AddressEntity direccion;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getNif() {
        return nif;
    }

    public void setNif(String nif) {
        this.nif = nif;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLastName1() {
        return lastName1;
    }

    public void setLastName1(String lastName1) {
        this.lastName1 = lastName1;
    }

    public String getLastName2() {
        return lastName2;
    }

    public void setLastName2(String lastName2) {
        this.lastName2 = lastName2;
    }

    public Date getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(Date birthDate) {
        this.birthDate = birthDate;
    }

    public Date getRegisterDate() {
        return registerDate;
    }

    public void setRegisterDate(Date registerDate) {
        this.registerDate = registerDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    public AddressEntity getDireccion() {
        return direccion;
    }

    public void setDireccion(AddressEntity direccion) {
        this.direccion = direccion;
    }

    public String fullName() {
        return name + " " + lastName1 + " " + lastName2;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ClientEntity that = (ClientEntity) o;
        return id == that.id && Objects.equals(status, that.status) && Objects.equals(nif, that.nif) && Objects.equals(name, that.name) && Objects.equals(lastName1, that.lastName1) && Objects.equals(lastName2, that.lastName2) && Objects.equals(birthDate, that.birthDate) && Objects.equals(registerDate, that.registerDate) && Objects.equals(endDate, that.endDate) && Objects.equals(direccion, that.direccion);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, status, nif, name, lastName1, lastName2, birthDate, registerDate, endDate, direccion);
    }
}
