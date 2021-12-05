package database;

import javax.persistence.*;
import java.sql.Date;
import java.util.Objects;

@Entity
@Table(name = "EburyAccount", schema = "grupo10DB", catalog = "")
public class EburyAccountEntity {
    private int id;
    private String status;
    private String accounttype;
    private Date registerdate;
    private Date closedate;

    @Id
    @Column(name = "id")
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Basic
    @Column(name = "status")
    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    @Basic
    @Column(name = "accounttype")
    public String getAccounttype() {
        return accounttype;
    }

    public void setAccounttype(String accounttype) {
        this.accounttype = accounttype;
    }

    @Basic
    @Column(name = "registerdate")
    public Date getRegisterdate() {
        return registerdate;
    }

    public void setRegisterdate(Date registerdate) {
        this.registerdate = registerdate;
    }

    @Basic
    @Column(name = "closedate")
    public Date getClosedate() {
        return closedate;
    }

    public void setClosedate(Date closedate) {
        this.closedate = closedate;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        EburyAccountEntity that = (EburyAccountEntity) o;
        return id == that.id && Objects.equals(status, that.status) && Objects.equals(accounttype, that.accounttype) && Objects.equals(registerdate, that.registerdate) && Objects.equals(closedate, that.closedate);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, status, accounttype, registerdate, closedate);
    }
}
