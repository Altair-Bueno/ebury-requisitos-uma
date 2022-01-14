package database.tables;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "BankAccount", schema = "grupo10DB", catalog = "")
public class BankAccountEntity {
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Id
    @Column(name = "IBAN")
    private String iban;
    @Basic
    @Column(name = "EburyAccount_id")
    private int eburyAccountId;
    @Basic
    @Column(name = "EburyAccount_owner")
    private int eburyAccountOwner;
    @Basic
    @Column(name = "SWIFT")
    private String swift;
    @Basic
    @Column(name = "curency")
    private String curency;
    @Basic
    @Column(name = "country")
    private String country;

    public String getIban() {
        return iban;
    }

    public void setIban(String iban) {
        this.iban = iban;
    }

    public int getEburyAccountId() {
        return eburyAccountId;
    }

    public void setEburyAccountId(int eburyAccountId) {
        this.eburyAccountId = eburyAccountId;
    }

    public int getEburyAccountOwner() {
        return eburyAccountOwner;
    }

    public void setEburyAccountOwner(int eburyAccountOwner) {
        this.eburyAccountOwner = eburyAccountOwner;
    }

    public String getSwift() {
        return swift;
    }

    public void setSwift(String swift) {
        this.swift = swift;
    }

    public String getCurency() {
        return curency;
    }

    public void setCurency(String curency) {
        this.curency = curency;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        BankAccountEntity that = (BankAccountEntity) o;
        return eburyAccountId == that.eburyAccountId && eburyAccountOwner == that.eburyAccountOwner && Objects.equals(iban, that.iban) && Objects.equals(swift, that.swift) && Objects.equals(curency, that.curency) && Objects.equals(country, that.country);
    }

    @Override
    public int hashCode() {
        return Objects.hash(iban, eburyAccountId, eburyAccountOwner, swift, curency, country);
    }
}
