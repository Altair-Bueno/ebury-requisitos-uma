package database.tables;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.sql.Date;
import java.util.Objects;

@Entity
@Table(name = "Relation", schema = "grupo10DB", catalog = "")
@IdClass(RelationEntityPK.class)
public class RelationEntity {
    @Id
    @ManyToOne
    @JoinColumn(name = "AssociatedStaff_DNI")
    private AssociatedStaffEntity associatedStaffDni;
    @Id
    @ManyToOne
    @JoinColumn(name = "Client_ID")
    private ClientEntity clientId;
    @Basic
    @Column(name = "start")
    private Date start;
    @Basic
    @Column(name = "end")
    private Date end;
    @Basic
    @Column(name = "authorised")
    private boolean authorised;

    public RelationEntity(AssociatedStaffEntity associatedStaffDni, ClientEntity clientId, Date start){
        this.associatedStaffDni = associatedStaffDni;
        this.clientId = clientId;
        this.start = start;
    }

    public RelationEntity() {

    }

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

    public Date getStart() {
        return start;
    }

    public void setStart(Date start) {
        this.start = start;
    }

    public Date getEnd() {
        return end;
    }

    public void setEnd(Date end) {
        this.end = end;
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
        if (!(o instanceof RelationEntity)) return false;
        RelationEntity that = (RelationEntity) o;
        return authorised == that.authorised && Objects.equals(associatedStaffDni, that.associatedStaffDni) && Objects.equals(clientId, that.clientId) && Objects.equals(start, that.start) && Objects.equals(end, that.end);
    }

    @Override
    public int hashCode() {
        return Objects.hash(associatedStaffDni, clientId, start, end, authorised);
    }
}
