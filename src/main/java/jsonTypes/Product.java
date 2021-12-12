package jsonTypes;

import java.io.Serializable;
import java.util.List;


public class Product implements Serializable {
    AccountHolder accountHolder;
    String productType;
    String productNumber;
    String status;
    String startDate;
    String endDate;

    public Product(AccountHolder accountHolder, String productType, String productNumber, String status, String startDate, String endDate) {
        this.accountHolder = accountHolder;
        this.productType = productType;
        this.productNumber = productNumber;
        this.status = status;
        this.startDate = startDate;
        this.endDate = endDate;
    }

    public AccountHolder getAccountHolder() {
        return accountHolder;
    }

    public void setAccountHolder(AccountHolder accountHolder) {
        this.accountHolder = accountHolder;
    }

    public String getProductType() {
        return productType;
    }

    public void setProductType(String productType) {
        this.productType = productType;
    }

    public String getProductNumber() {
        return productNumber;
    }

    public void setProductNumber(String productNumber) {
        this.productNumber = productNumber;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getStartDate() {
        return startDate;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    public String getEndDate() {
        return endDate;
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }
}
