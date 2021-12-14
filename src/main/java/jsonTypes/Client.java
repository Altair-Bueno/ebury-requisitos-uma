package jsonTypes;

import java.util.List;

public class Client {
    List<ProductClient> products;
    Boolean activeCustomer;
    String dateOfBirth;
    Name name;
    List<Address> address;

    public Client(List<ProductClient> products, Boolean activeCustomer, String dateOfBirth, Name name, List<Address> address) {
        this.products = products;
        this.activeCustomer = activeCustomer;
        this.dateOfBirth = dateOfBirth;
        this.name = name;
        this.address = address;
    }

    public List<ProductClient> getProducts(){return products;}

    public void setProducts(List<ProductClient> products){
        this.products = products;
    }

    public Boolean getActiveCustomer() {
        return activeCustomer;
    }

    public void setActiveCustomer(Boolean activeCustomer) {
        this.activeCustomer = activeCustomer;
    }

    public String getDateOfBirth(){
        return dateOfBirth;
    }

    public void setDateOfBirth(String dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
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
}
