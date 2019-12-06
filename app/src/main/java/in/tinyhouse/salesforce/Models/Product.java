package in.tinyhouse.salesforce.Models;

public class Product {

    private String id;
    private String productName;
    private String barcodeData;
    private String price;
    private String timeAdded;

    public  Product () {}

    //Setter and getter functions for id
    public void setId(String Id){
        this.id = Id;
    }
    public String getId(){
        return id;
    }

    //Setter and getter functions for productName
    public void setProductName(String nameOfProduct){
        this.productName = nameOfProduct;
    }
    public String getProductName(){
        return productName;
    }

    //Setter and getter functions for barcodeData
    private void setBarcodeData(String barcodeOfProduct){
        this.barcodeData = barcodeOfProduct;
    }
    private String getBarcodeData(){
        return barcodeData;
    }



}
