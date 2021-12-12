package jsonTypes;

import java.io.Serializable;
import java.util.List;

public class AccountHolder implements Serializable {

    Boolean activeCustomer;
    String accountType;
    Name name;
    List<Adress> adress;

    public Boolean getActiveCustomer() {
        return activeCustomer;
    }

    public void setActiveCustomer(Boolean activeCustomer) {
        this.activeCustomer = activeCustomer;
    }

    public String getAccountType() {
        return accountType;
    }

    public void setAccountType(String accountType) {
        this.accountType = accountType;
    }

    public Name getName() {
        return name;
    }

    public void setName(Name name) {
        this.name = name;
    }

    public List<Adress> getAdress() {
        return adress;
    }

    public void setAdress(List<Adress> adress) {
        this.adress = adress;
    }

    public AccountHolder(Boolean activeCustomer, String accountType, Name name, List<Adress> adress) {
        this.activeCustomer = activeCustomer;
        this.accountType = accountType;
        this.name = name;
        this.adress = adress;
    }
}
