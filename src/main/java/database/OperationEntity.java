package database;

import javax.persistence.*;
import java.sql.Date;
import java.util.Objects;

@Entity
@Table(name = "Operation", schema = "grupo10DB", catalog = "")
@IdClass(OperationEntityPK.class)
public class OperationEntity {
    private int id;
    private String bankAccountIban;
    private int eburyAccountId;
    private Date date;
    private double amount;
    private Double conversionrate;
    private Double comission;
    private String beneficiary;

    @Id
    @Column(name = "id")
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Id
    @Column(name = "BankAccount_IBAN")
    public String getBankAccountIban() {
        return bankAccountIban;
    }

    public void setBankAccountIban(String bankAccountIban) {
        this.bankAccountIban = bankAccountIban;
    }

    @Id
    @Column(name = "EburyAccount_id")
    public int getEburyAccountId() {
        return eburyAccountId;
    }

    public void setEburyAccountId(int eburyAccountId) {
        this.eburyAccountId = eburyAccountId;
    }

    @Basic
    @Column(name = "date")
    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    @Basic
    @Column(name = "amount")
    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    @Basic
    @Column(name = "conversionrate")
    public Double getConversionrate() {
        return conversionrate;
    }

    public void setConversionrate(Double conversionrate) {
        this.conversionrate = conversionrate;
    }

    @Basic
    @Column(name = "comission")
    public Double getComission() {
        return comission;
    }

    public void setComission(Double comission) {
        this.comission = comission;
    }

    @Basic
    @Column(name = "beneficiary")
    public String getBeneficiary() {
        return beneficiary;
    }

    public void setBeneficiary(String beneficiary) {
        this.beneficiary = beneficiary;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        OperationEntity that = (OperationEntity) o;
        return id == that.id && eburyAccountId == that.eburyAccountId && Double.compare(that.amount, amount) == 0 && Objects.equals(bankAccountIban, that.bankAccountIban) && Objects.equals(date, that.date) && Objects.equals(conversionrate, that.conversionrate) && Objects.equals(comission, that.comission) && Objects.equals(beneficiary, that.beneficiary);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, bankAccountIban, eburyAccountId, date, amount, conversionrate, comission, beneficiary);
    }
}
