package database.tables;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Objects;

public class LinkEntityPK implements Serializable {
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Id
    @ManyToOne
    @JoinColumn(name = "Relation_AssociatedStaff_DNI")
    private AssociatedStaffEntity relationAssociatedStaffDni;
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Id
    @ManyToOne
    @JoinColumn(name = "Relation_Client_ID")
    private ClientEntity relationClientId;
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Id
    @ManyToOne
    @JoinColumn(name = "EburyAccount_id")
    private EburyAccountEntity eburyAccountId;

    public LinkEntityPK() {
    }

    public LinkEntityPK(AssociatedStaffEntity relationAssociatedStaffDni, ClientEntity relationClientId) {
        this.relationAssociatedStaffDni = relationAssociatedStaffDni;
        this.relationClientId = relationClientId;
    }

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
        LinkEntityPK that = (LinkEntityPK) o;
        return relationClientId == that.relationClientId && eburyAccountId == that.eburyAccountId && Objects.equals(relationAssociatedStaffDni, that.relationAssociatedStaffDni);
    }

    @Override
    public int hashCode() {
        return Objects.hash(relationAssociatedStaffDni, relationClientId, eburyAccountId);
    }
}
