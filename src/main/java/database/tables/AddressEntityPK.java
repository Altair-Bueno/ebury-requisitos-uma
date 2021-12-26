package database.tables;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Objects;

public class AddressEntityPK implements Serializable {
    @Column(name = "ID")
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @ManyToOne
    @JoinColumn(name = "Client_ID")
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
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
