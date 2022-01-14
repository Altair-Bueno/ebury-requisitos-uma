package database.tables;

import database.types.Status;

import javax.persistence.*;
import java.sql.Date;
import java.util.Objects;

@Entity
@Table(name = "EburyAccount", schema = "grupo10DB", catalog = "")
public class EburyAccountEntity {
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Id
    @Column(name = "id")
    private int id;
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "BankAccount_IBAN")
    private BankAccountEntity bankAccount;
    @ManyToOne
    @JoinColumn(name = "owner")
    private ClientEntity owner;
    @Basic
    @Column(name = "status")
    @Enumerated(EnumType.STRING)
    private Status status;
    @Basic
    @Column(name = "accounttype")
    private String accounttype;
    @Basic
    @Column(name = "registerdate")
    private Date registerdate;
    @Basic
    @Column(name = "closedate")
    private Date closedate;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public BankAccountEntity getBankAccount() {
        return bankAccount;
    }

    public void setBankAccount(BankAccountEntity bankAccount) {
        this.bankAccount = bankAccount;
    }

    public String getInfo() {
        return "[" + bankAccount.getIban() + "] " + "[" + owner.fullName() + "] " + "[" + getStatus() + "] " + "[" + getAccounttype() + "] " + "[" + getRegisterdate() + "] " + "[" + getClosedate() + "]";
    }

    public ClientEntity getOwner() {
        return owner;
    }

    public void setOwner(ClientEntity owner) {
        this.owner = owner;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public String getAccounttype() {
        return accounttype;
    }

    public void setAccounttype(String accounttype) {
        this.accounttype = accounttype;
    }

    public Date getRegisterdate() {
        return registerdate;
    }

    public void setRegisterdate(Date registerdate) {
        this.registerdate = registerdate;
    }

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
        return id == that.id && owner.equals(that.owner);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, bankAccount, owner);
    }
}
