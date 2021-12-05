package database;

import javax.persistence.Column;
import javax.persistence.Id;
import java.io.Serializable;
import java.util.Objects;

public class OperationEntityPK implements Serializable {
    private int id;
    private String bankAccountIban;
    private int eburyAccountId;

    @Column(name = "id")
    @Id
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Column(name = "BankAccount_IBAN")
    @Id
    public String getBankAccountIban() {
        return bankAccountIban;
    }

    public void setBankAccountIban(String bankAccountIban) {
        this.bankAccountIban = bankAccountIban;
    }

    @Column(name = "EburyAccount_id")
    @Id
    public int getEburyAccountId() {
        return eburyAccountId;
    }

    public void setEburyAccountId(int eburyAccountId) {
        this.eburyAccountId = eburyAccountId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        OperationEntityPK that = (OperationEntityPK) o;
        return id == that.id && eburyAccountId == that.eburyAccountId && Objects.equals(bankAccountIban, that.bankAccountIban);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, bankAccountIban, eburyAccountId);
    }
}
