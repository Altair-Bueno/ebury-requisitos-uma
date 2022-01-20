package database.tables;

import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import java.io.Serializable;
import java.util.Objects;


public class AddressAssociatedStaffEntityPK implements Serializable {
    @Id
    private int id;

    @ManyToOne
    @JoinColumn(name = "AssociatedStaff_DNI")
    private AssociatedStaffEntity AssociatedStaffDNI;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public AssociatedStaffEntity getClientId() {
        return AssociatedStaffDNI;
    }

    public void setClientId(AssociatedStaffEntity AssociatedStaffDNI) {
        this.AssociatedStaffDNI = AssociatedStaffDNI;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        AddressAssociatedStaffEntityPK that = (AddressAssociatedStaffEntityPK) o;
        return id == that.id && AssociatedStaffDNI == that.AssociatedStaffDNI;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, AssociatedStaffDNI);
    }
}
