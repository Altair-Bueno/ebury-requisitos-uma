package database.tables;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "Login", schema = "grupo10DB", catalog = "")
public class LoginEntity {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "user")
    private String user;
    @Basic
    @Column(name = "password")
    private String password;
    @Basic
    @Column(name = "rol")
    private String rol;
    @ManyToOne
    @JoinColumn(name = "AS_FK")
    private AssociatedStaffEntity asFk;

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getRol() {
        return rol;
    }

    public void setRol(String rol) {
        this.rol = rol;
    }

    public AssociatedStaffEntity getAsFk() {
        return asFk;
    }

    public void setAsFk(AssociatedStaffEntity asFk) {
        this.asFk = asFk;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        LoginEntity that = (LoginEntity) o;
        return Objects.equals(user, that.user) && Objects.equals(password, that.password) && Objects.equals(rol, that.rol) && Objects.equals(asFk, that.asFk);
    }

    @Override
    public int hashCode() {
        return Objects.hash(user, password, rol, asFk);
    }
}
