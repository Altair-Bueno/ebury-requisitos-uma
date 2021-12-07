package database.tables;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.io.Serializable;
import java.util.Objects;

public class RelationEntityPK implements Serializable {
    @Column(name = "AssociatedStaff_DNI")
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private String associatedStaffDni;
    @Column(name = "Client_ID")
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int clientId;

    public String getAssociatedStaffDni() {
        return associatedStaffDni;
    }

    public void setAssociatedStaffDni(String associatedStaffDni) {
        this.associatedStaffDni = associatedStaffDni;
    }

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
