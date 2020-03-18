package in.tinyhouse.salesforce.models;

public class Customer {

    private String id;
    private String name;
    private String phoneNumber;
    private String[] orders;

    //Getter and Setter functions for id
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    //Getter and Setter functions for name
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    //Getter and Setter functions for phoneNumber
    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    //Getter and Setter functions for orders
    public String[] getOrders() {
        return orders;
    }

    public void setOrders(String[] orders) {
        this.orders = orders;
    }
}
