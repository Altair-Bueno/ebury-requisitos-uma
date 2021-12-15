package database.tables;

import javax.persistence.*;
import java.sql.Date;
import java.util.Objects;

@Entity
@Table(name = "Operation", schema = "grupo10DB", catalog = "")
@IdClass(OperationEntityPK.class)
public class OperationEntity {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @ManyToOne
    @JoinColumn(name = "EburyAccount_id")
    public EburyAccountEntity eburyAccount;
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "id")
    private int id;
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "BankAccount_IBAN")
    private String bankAccountIban;
    @Basic
    @Column(name = "date")
    private Date date;
    @Basic
    @Column(name = "amount")
    private double amount;
    @Basic
    @Column(name = "conversionrate")
    private Double conversionrate;
    @Basic
    @Column(name = "comission")
    private Double comission;
    @Basic
    @Column(name = "beneficiary")
    private String beneficiary;

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

    public EburyAccountEntity getEburyAccount() {
        return eburyAccount;
    }

    public void setEburyAccount(EburyAccountEntity eburyAccount) {
        this.eburyAccount = eburyAccount;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public Double getConversionrate() {
        return conversionrate;
    }

    public void setConversionrate(Double conversionrate) {
        this.conversionrate = conversionrate;
    }

    public Double getComission() {
        return comission;
    }

    public void setComission(Double comission) {
        this.comission = comission;
    }

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
        return id == that.id && eburyAccount.equals(that.eburyAccount) && Double.compare(that.amount, amount) == 0 && Objects.equals(bankAccountIban, that.bankAccountIban) && Objects.equals(date, that.date) && Objects.equals(conversionrate, that.conversionrate) && Objects.equals(comission, that.comission) && Objects.equals(beneficiary, that.beneficiary);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, bankAccountIban, eburyAccount, date, amount, conversionrate, comission, beneficiary);
    }
}
