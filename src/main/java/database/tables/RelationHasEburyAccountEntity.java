package database.tables;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "Relation_has_EburyAccount", schema = "grupo10DB", catalog = "")
@IdClass(RelationHasEburyAccountEntityPK.class)
public class RelationHasEburyAccountEntity {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "Relation_AssociatedStaff_DNI")
    private String relationAssociatedStaffDni;
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "Relation_Client_ID")
    private int relationClientId;
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "EburyAccount_id")
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
        RelationHasEburyAccountEntity that = (RelationHasEburyAccountEntity) o;
        return relationClientId == that.relationClientId && eburyAccountId == that.eburyAccountId && Objects.equals(relationAssociatedStaffDni, that.relationAssociatedStaffDni);
    }

    @Override
    public int hashCode() {
        return Objects.hash(relationAssociatedStaffDni, relationClientId, eburyAccountId);
    }
}
