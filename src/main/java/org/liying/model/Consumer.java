package org.liying.model;
public class Consumer {
    private long id;
    private String name;
    private  String address;
    private  String phone;
    private  long shippingPlatformID;
    public  Consumer(){}
    public long getId() {
        return id;
    }
    public void setId(long id) {
        this.id = id;
    }
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public String getAddress() {
        return address;
    }
    public void setAddress(String address) {
        this.address = address;
    }
    public String getPhone() {
        return phone;
    }
    public void setPhone(String phone) {
        this.phone = phone;
    }
    public long getShippingPlatformID() {
        return shippingPlatformID;
    }
    public void setShippingPlatformID(long shippingPlatformID) {
        this.shippingPlatformID = shippingPlatformID;
    }
}
