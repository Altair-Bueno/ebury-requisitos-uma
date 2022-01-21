package database.tables;

import database.HibernateStartUp;
import database.types.Status;
import org.hibernate.Session;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.Date;
import java.util.List;
import java.util.Objects;

@Entity
@Table(name = "AssociatedStaff", schema = "grupo10DB", catalog = "")
public class AssociatedStaffEntity {
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
    @Basic
    @Column(name = "birth_date")
    private Date birthDate;
    @Basic
    @Column(name = "tipoAsociado")
    private String tipoAsociado;

    public AssociatedStaffEntity(String dni, String name, String lastName1, String lastName2, Date birthDate, String tipoAsociado){
        this.dni = dni;
        this.name = name;
        this.lastName1 = lastName1;
        this.lastName2 = lastName2;
        this.state = Status.Active;
        this.birthDate = birthDate;
        this.tipoAsociado = tipoAsociado;
    }

    public AssociatedStaffEntity() {

    }

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

    public Date getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(Date birthDate) {
        this.birthDate = birthDate;
    }

    public String getTipoAsociado() {
        return tipoAsociado;
    }

    public void setTipoAsociado(String tipoAsociado) {
        this.tipoAsociado = tipoAsociado;
    }

    @SuppressWarnings("unchecked")
    public List<LoginEntity> getLoginasstaff() {
        try(Session session = HibernateStartUp.getSessionFactory().openSession()) {
            var query = session.createQuery("from LoginEntity where asFk = :associated");
            query.setParameter("associated",this);
            return (List<LoginEntity>) query.getResultList();
        } catch (Exception ex) {
            return null;
        }
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
