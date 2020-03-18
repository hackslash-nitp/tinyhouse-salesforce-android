package in.tinyhouse.salesforce.models;

import java.util.HashMap;

public class Bill {

    private String id;
    private String billNumber;
    private Float totalAmount;
    private Integer itemsCount;
    private String customerId;
    private String customerName;
    private String customerPhone;
    private HashMap<String, Integer> productList;
    private String paidAmount;
    private String returnedAmount;
    private String timeStamp;

    public Bill() {
    }

    //Getter and Setter functions for id
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    //Getter and Setter functions for billNumber
    public String getBillNumber() {
        return billNumber;
    }

    public void setBillNumber(String billNumber) {
        this.billNumber = billNumber;
    }

    //Getter and Setter functions for totalAmount
    public Float getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(Float totalAmount) {
        this.totalAmount = totalAmount;
    }

    //Getter and Setter functions for itemsCount
    public Integer getItemsCount() {
        return itemsCount;
    }

    public void setItemsCount(Integer itemsCount) {
        this.itemsCount = itemsCount;
    }

    //Getter and Setter functions for customerId
    public String getCustomerId() {
        return customerId;
    }

    public void setCustomerId(String customerId) {
        this.customerId = customerId;
    }

    //Getter and Setter functions for customerName
    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    //Getter and Setter functions for customerPhone
    public String getCustomerPhone() {
        return customerPhone;
    }

    public void setCustomerPhone(String customerPhone) {
        this.customerPhone = customerPhone;
    }

    //Getter and Setter functions for productList
    public HashMap<String, Integer> getProductList() {
        return productList;
    }

    public void setProductList(HashMap<String, Integer> productList) {
        this.productList = productList;
    }

    //Getter and Setter functions for paidAmount
    public String getPaidAmount() {
        return paidAmount;
    }

    public void setPaidAmount(String paidAmount) {
        this.paidAmount = paidAmount;
    }

    //Getter and Setter functions for returnedAmount
    public String getReturnedAmount() {
        return returnedAmount;
    }

    public void setReturnedAmount(String returnedAmount) {
        this.returnedAmount = returnedAmount;
    }

    //Getter and Setter functions for timeStamp
    public String getTimeStamp() {
        return timeStamp;
    }

    public void setTimeStamp(String timeStamp) {
        this.timeStamp = timeStamp;
    }
}
