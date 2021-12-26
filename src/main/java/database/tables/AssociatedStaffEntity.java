package database.tables;

import database.types.Status;

import javax.persistence.*;
import java.util.List;
import java.util.Objects;

@Entity
@Table(name = "AssociatedStaff", schema = "grupo10DB", catalog = "")
public class AssociatedStaffEntity {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "DNI")
    private String dni;
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
    @Column(name = "state")
    @Enumerated(EnumType.STRING)
    private Status state;
    @OneToMany
    @JoinColumn(name = "AS_FK", insertable = false, updatable = false)
    private List<LoginEntity> loginasstaff;

    public String getDni() {
        return dni;
    }

    public void setDni(String dni) {
        this.dni = dni;
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

    public Status getState() {
        return state;
    }

    public void setState(Status state) {
        this.state = state;
    }

    public List<LoginEntity> getLoginasstaff() {
        return loginasstaff;
    }

    public void setLoginasstaff(List<LoginEntity> loginasstaff) {
        this.loginasstaff = loginasstaff;
    }

    public String fullName() {
        return name + " " + lastName1 + " " + lastName2;
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
