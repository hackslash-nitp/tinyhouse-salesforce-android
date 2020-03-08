package in.tinyhouse.salesforce.models;

public class User {
    private String id;
    private String name;
    private String email;
    private String phoneNumber;
    private int orderProcessed;
    //constructor with no input arguments
    public User(){

    }
    /*
    GETTER METHODS
     **/

    //getter method for name
    public String getName(){
        return name;
    }
    //getter method for email
    public String getEmail(){
        return email;
    }
    //getter method for phone number
    public String getPhoneNumber(){
        return phoneNumber;
    }
    //getter method for id
    public String getId(){
        return id;
    }
    //getter method for order processed
    public int getOrderProcessed(){
        return orderProcessed;
    }

    /*
    SETTER METHODS
    **/

    //setter method for id
    public void setId(String id){
        this.id=id;
    }
    //setter method for name
    public void setName(String name){
        this.name=name;
    }
    //setter method for email
    public void setEmail(String email){
        this.email=email;
    }
    //setter method for phone number
    public void setPhoneNumber(String phoneNumber){
        this.phoneNumber=phoneNumber;
    }
    //setter method for order processed
    public void setOrderProcessed(int orderProcessed){
        this.orderProcessed=orderProcessed;
    }
}
