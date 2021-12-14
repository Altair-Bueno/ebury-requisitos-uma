package jsonTypes;

import java.util.List;

public class ProductClient {
    String productType;
    String productNumber;
    String status;

    public ProductClient(String productType, String productNumber, String status) {
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

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

}
