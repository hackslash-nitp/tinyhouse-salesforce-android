package in.tinyhouse.salesforce.models;

public class Product {

    private String id;
    private String productName;
    private String barcodeData;
    private String price;
    private String timeAdded;

    public Product() {
    }

    public String getId() {
        return id;
    }

    //Setter and getter functions for id
    public void setId(String Id) {
        this.id = Id;
    }

    public String getProductName() {
        return productName;
    }

    //Setter and getter functions for productName
    public void setProductName(String nameOfProduct) {
        this.productName = nameOfProduct;
    }

    public String getBarcodeData() {
        return barcodeData;
    }

    //Setter and getter functions for barcodeData
    public void setBarcodeData(String barcodeOfProduct) {
        this.barcodeData = barcodeOfProduct;
    }

    public String getPrice() {
        return price;
    }

    //Setter and getter functions for price
    public void setPrice(String productPrice) {
        this.price = productPrice;
    }

    public String getTimeAdded() {
        return timeAdded;
    }

    //Setter and getter functions for timeAdded
    public void setTimeAdded(String time) {
        this.timeAdded = time;
    }

}
