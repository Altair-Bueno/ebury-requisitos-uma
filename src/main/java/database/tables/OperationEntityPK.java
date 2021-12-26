package database.tables;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Objects;

public class OperationEntityPK implements Serializable {
    @Id
    @ManyToOne
    @JoinColumn(name = "EburyAccount_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private EburyAccountEntity eburyid;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @ManyToOne
    @JoinColumn(name = "BankAccount_IBAN")
    private BankAccountEntity bankAccountIban;
    @Column(name = "EburyAccount_id")
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int eburyAccount;

    public EburyAccountEntity getEburyid() {
        return eburyid;
    }

    public void setEburyid(EburyAccountEntity id) {
        this.eburyid = id;
    }

    public BankAccountEntity getBankAccountIban() {
        return bankAccountIban;
    }

    public void setBankAccountIban(BankAccountEntity bankAccountIban) {
        this.bankAccountIban = bankAccountIban;
    }

    public int getEburyAccount() {
        return eburyAccount;
    }

    public void setEburyAccount(int eburyAccount) {
        this.eburyAccount = eburyAccount;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        OperationEntityPK that = (OperationEntityPK) o;
        return eburyid == that.eburyid && eburyAccount == (that.eburyAccount) && Objects.equals(bankAccountIban, that.bankAccountIban);
    }

    @Override
    public int hashCode() {
        return Objects.hash(eburyid, bankAccountIban, eburyAccount);
    }
}
