package database.tables;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Objects;

public class RelationEntityPK implements Serializable {
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Id
    @ManyToOne
    @JoinColumn(name = "AssociatedStaff_DNI")
    private AssociatedStaffEntity associatedStaffDni;
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Id
    @ManyToOne
    @JoinColumn(name = "Client_ID")
    private ClientEntity clientId;

    public AssociatedStaffEntity getAssociatedStaffDni() {
        return associatedStaffDni;
    }

    public void setAssociatedStaffDni(AssociatedStaffEntity associatedStaffDni) {
        this.associatedStaffDni = associatedStaffDni;
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
        RelationEntityPK that = (RelationEntityPK) o;
        return clientId == that.clientId && Objects.equals(associatedStaffDni, that.associatedStaffDni);
    }

    @Override
    public int hashCode() {
        return Objects.hash(associatedStaffDni, clientId);
    }
}
