package database.tables;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.io.Serializable;
import java.util.Objects;

public class OperationEntityPK implements Serializable {
    @Column(name = "id")
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @Column(name = "BankAccount_IBAN")
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private String bankAccountIban;
    @Column(name = "EburyAccount_id")
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int eburyAccount;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getBankAccountIban() {
        return bankAccountIban;
    }

    public void setBankAccountIban(String bankAccountIban) {
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
        return id == that.id && eburyAccount == (that.eburyAccount) && Objects.equals(bankAccountIban, that.bankAccountIban);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, bankAccountIban, eburyAccount);
    }
}
