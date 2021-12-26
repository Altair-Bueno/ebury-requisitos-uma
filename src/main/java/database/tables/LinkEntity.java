package database.tables;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "Link", schema = "grupo10DB", catalog = "")
@IdClass(LinkEntityPK.class)
public class LinkEntity {
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
    @Basic
    @Column(name = "authorised")
    private boolean authorised;

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

    public boolean isAuthorised() {
        return authorised;
    }

    public void setAuthorised(boolean authorised) {
        this.authorised = authorised;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof LinkEntity)) return false;
        LinkEntity that = (LinkEntity) o;
        return authorised == that.authorised && Objects.equals(relationAssociatedStaffDni, that.relationAssociatedStaffDni) && Objects.equals(relationClientId, that.relationClientId) && Objects.equals(eburyAccountId, that.eburyAccountId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(relationAssociatedStaffDni, relationClientId, eburyAccountId, authorised);
    }
}
