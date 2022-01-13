package database.tables;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Objects;


public class AddressEntityPK implements Serializable {
    @Id
    private int id;

    @ManyToOne
    @JoinColumn(name = "Client_ID")
    private ClientEntity clientId;

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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        AddressEntityPK that = (AddressEntityPK) o;
        return id == that.id && clientId == that.clientId;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, clientId);
    }
}
