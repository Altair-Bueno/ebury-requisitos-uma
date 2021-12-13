package jsonTypes;

import java.io.Serializable;
import java.util.List;

public class AccountHolder implements Serializable {

    Boolean activeCustomer;
    String accountType;
    Name name;
    List<Address> address;

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

    public List<Address> getAddress() {
        return address;
    }

    public void setAdress(List<Address> address) {
        this.address = address;
    }

    public AccountHolder(Boolean activeCustomer, String accountType, Name name, List<Address> address) {
        this.activeCustomer = activeCustomer;
        this.accountType = accountType;
        this.name = name;
        this.address = address;
    }
}
