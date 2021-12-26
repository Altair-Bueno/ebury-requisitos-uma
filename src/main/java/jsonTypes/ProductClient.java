package jsonTypes;

import database.types.Status;

public class ProductClient {
    String productType;
    String productNumber;
    Status status;

    public ProductClient(String productType, String productNumber, Status status) {
        this.productType = productType;
        this.productNumber = productNumber;
        this.status = status;
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

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

}
