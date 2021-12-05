package database;

import javax.persistence.Column;
import javax.persistence.Id;
import java.io.Serializable;
import java.util.Objects;

public class AdressEntityPK implements Serializable {
    private int id;
    private int clientId;

    @Column(name = "ID")
    @Id
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Column(name = "Client_ID")
    @Id
    public int getClientId() {
        return clientId;
    }

    public void setClientId(int clientId) {
        this.clientId = clientId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        AdressEntityPK that = (AdressEntityPK) o;
        return id == that.id && clientId == that.clientId;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, clientId);
    }
}
