package database;

import javax.persistence.Column;
import javax.persistence.Id;
import java.io.Serializable;
import java.util.Objects;

public class RelationEntityPK implements Serializable {
    private String associatedStaffDni;
    private int clientId;

    @Column(name = "AssociatedStaff_DNI")
    @Id
    public String getAssociatedStaffDni() {
        return associatedStaffDni;
    }

    public void setAssociatedStaffDni(String associatedStaffDni) {
        this.associatedStaffDni = associatedStaffDni;
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
        RelationEntityPK that = (RelationEntityPK) o;
        return clientId == that.clientId && Objects.equals(associatedStaffDni, that.associatedStaffDni);
    }

    @Override
    public int hashCode() {
        return Objects.hash(associatedStaffDni, clientId);
    }
}
