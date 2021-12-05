package database;

import javax.persistence.Column;
import javax.persistence.Id;
import java.io.Serializable;
import java.util.Objects;

public class RelationHasEburyAccountEntityPK implements Serializable {
    private String relationAssociatedStaffDni;
    private int relationClientId;
    private int eburyAccountId;

    @Column(name = "Relation_AssociatedStaff_DNI")
    @Id
    public String getRelationAssociatedStaffDni() {
        return relationAssociatedStaffDni;
    }

    public void setRelationAssociatedStaffDni(String relationAssociatedStaffDni) {
        this.relationAssociatedStaffDni = relationAssociatedStaffDni;
    }

    @Column(name = "Relation_Client_ID")
    @Id
    public int getRelationClientId() {
        return relationClientId;
    }

    public void setRelationClientId(int relationClientId) {
        this.relationClientId = relationClientId;
    }

    @Column(name = "EburyAccount_id")
    @Id
    public int getEburyAccountId() {
        return eburyAccountId;
    }

    public void setEburyAccountId(int eburyAccountId) {
        this.eburyAccountId = eburyAccountId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        RelationHasEburyAccountEntityPK that = (RelationHasEburyAccountEntityPK) o;
        return relationClientId == that.relationClientId && eburyAccountId == that.eburyAccountId && Objects.equals(relationAssociatedStaffDni, that.relationAssociatedStaffDni);
    }

    @Override
    public int hashCode() {
        return Objects.hash(relationAssociatedStaffDni, relationClientId, eburyAccountId);
    }
}
