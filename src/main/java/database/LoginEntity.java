package database;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "Login", schema = "grupo10DB", catalog = "")
public class LoginEntity {
    private String user;
    private String password;
    private String rol;

    @Id
    @Column(name = "user")
    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    @Basic
    @Column(name = "password")
    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Basic
    @Column(name = "rol")
    public String getRol() {
        return rol;
    }

    public void setRol(String rol) {
        this.rol = rol;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        LoginEntity that = (LoginEntity) o;
        return Objects.equals(user, that.user) && Objects.equals(password, that.password) && Objects.equals(rol, that.rol);
    }

    @Override
    public int hashCode() {
        return Objects.hash(user, password, rol);
    }
}
