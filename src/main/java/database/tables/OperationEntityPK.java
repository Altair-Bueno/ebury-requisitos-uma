package database.tables;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Objects;

public class OperationEntityPK implements Serializable {
    @Id
    @ManyToOne
    @JoinColumn(name = "EburyAccount_id")
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private EburyAccountEntity eburyAccount;
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @ManyToOne
    @JoinColumn(name = "BankAccount_IBAN")
    private BankAccountEntity bankAccountIban;

    public BankAccountEntity getBankAccountIban() {
        return bankAccountIban;
    }

    public void setBankAccountIban(BankAccountEntity bankAccountIban) {
        this.bankAccountIban = bankAccountIban;
    }

    public EburyAccountEntity getEburyAccount() {
        return eburyAccount;
    }

    public void setEburyAccount(EburyAccountEntity eburyAccount) {
        this.eburyAccount = eburyAccount;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        OperationEntityPK that = (OperationEntityPK) o;
        return eburyAccount == (that.eburyAccount) && Objects.equals(bankAccountIban, that.bankAccountIban);
    }

    @Override
    public int hashCode() {
        return Objects.hash(bankAccountIban, eburyAccount);
    }
}
