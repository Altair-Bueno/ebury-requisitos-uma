package database;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "AssociatedStaff", schema = "grupo10DB", catalog = "")
public class AssociatedStaffEntity {
    private String dni;
    private String name;
    private String lastName1;
    private String lastName2;
    private String state;

    @Id
    @Column(name = "DNI")
    public String getDni() {
        return dni;
    }

    public void setDni(String dni) {
        this.dni = dni;
    }

    @Basic
    @Column(name = "name")
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Basic
    @Column(name = "last_name1")
    public String getLastName1() {
        return lastName1;
    }

    public void setLastName1(String lastName1) {
        this.lastName1 = lastName1;
    }

    @Basic
    @Column(name = "last_name2")
    public String getLastName2() {
        return lastName2;
    }

    public void setLastName2(String lastName2) {
        this.lastName2 = lastName2;
    }

    @Basic
    @Column(name = "state")
    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        AssociatedStaffEntity that = (AssociatedStaffEntity) o;
        return Objects.equals(dni, that.dni) && Objects.equals(name, that.name) && Objects.equals(lastName1, that.lastName1) && Objects.equals(lastName2, that.lastName2) && Objects.equals(state, that.state);
    }

    @Override
    public int hashCode() {
        return Objects.hash(dni, name, lastName1, lastName2, state);
    }
}
