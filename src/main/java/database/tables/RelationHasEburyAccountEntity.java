package database.tables;

import jsonTypes.Client;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "Relation_has_EburyAccount", schema = "grupo10DB", catalog = "")
@IdClass(RelationHasEburyAccountEntityPK.class)
public class RelationHasEburyAccountEntity {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @ManyToOne
    @JoinColumn(name = "Relation_AssociatedStaff_DNI")
    private AssociatedStaffEntity relationAssociatedStaffDni;
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @ManyToOne
    @JoinColumn(name = "Relation_Client_ID")
    private ClientEntity relationClientId;
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @ManyToOne
    @JoinColumn(name = "EburyAccount_id")
    private EburyAccountEntity eburyAccountId;

    public AssociatedStaffEntity getRelationAssociatedStaffDni() {
        return relationAssociatedStaffDni;
    }

    public void setRelationAssociatedStaffDni(AssociatedStaffEntity relationAssociatedStaffDni) {
        this.relationAssociatedStaffDni = relationAssociatedStaffDni;
    }

    public ClientEntity getRelationClientId() {
        return relationClientId;
    }

    public void setRelationClientId(ClientEntity relationClientId) {
        this.relationClientId = relationClientId;
    }

    public EburyAccountEntity getEburyAccountId() {
        return eburyAccountId;
    }

    public void setEburyAccountId(EburyAccountEntity eburyAccountId) {
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
