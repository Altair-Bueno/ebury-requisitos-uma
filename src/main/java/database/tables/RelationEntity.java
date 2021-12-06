package database.tables;

import javax.persistence.*;
import java.sql.Date;
import java.util.Objects;

@Entity
@Table(name = "Relation", schema = "grupo10DB", catalog = "")
@IdClass(RelationEntityPK.class)
public class RelationEntity {
    private String associatedStaffDni;
    private int clientId;
    private Date start;
    private Date end;

    @Id
    @Column(name = "AssociatedStaff_DNI")
    public String getAssociatedStaffDni() {
        return associatedStaffDni;
    }

    public void setAssociatedStaffDni(String associatedStaffDni) {
        this.associatedStaffDni = associatedStaffDni;
    }

    @Id
    @Column(name = "Client_ID")
    public int getClientId() {
        return clientId;
    }

    public void setClientId(int clientId) {
        this.clientId = clientId;
    }

    @Basic
    @Column(name = "start")
    public Date getStart() {
        return start;
    }

    public void setStart(Date start) {
        this.start = start;
    }

    @Basic
    @Column(name = "end")
    public Date getEnd() {
        return end;
    }

    public void setEnd(Date end) {
        this.end = end;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        RelationEntity that = (RelationEntity) o;
        return clientId == that.clientId && Objects.equals(associatedStaffDni, that.associatedStaffDni) && Objects.equals(start, that.start) && Objects.equals(end, that.end);
    }

    @Override
    public int hashCode() {
        return Objects.hash(associatedStaffDni, clientId, start, end);
    }
}
