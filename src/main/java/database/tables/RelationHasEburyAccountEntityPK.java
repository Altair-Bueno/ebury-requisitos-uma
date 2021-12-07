package database.tables;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.io.Serializable;
import java.util.Objects;

public class RelationHasEburyAccountEntityPK implements Serializable {
    @Column(name = "Relation_AssociatedStaff_DNI")
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private String relationAssociatedStaffDni;
    @Column(name = "Relation_Client_ID")
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int relationClientId;
    @Column(name = "EburyAccount_id")
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int eburyAccountId;

    public String getRelationAssociatedStaffDni() {
        return relationAssociatedStaffDni;
    }

    public void setRelationAssociatedStaffDni(String relationAssociatedStaffDni) {
        this.relationAssociatedStaffDni = relationAssociatedStaffDni;
    }

    public int getRelationClientId() {
        return relationClientId;
    }

    public void setRelationClientId(int relationClientId) {
        this.relationClientId = relationClientId;
    }

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
