package org.liying.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import javax.persistence.*;
import java.util.Set;

// write persistent class by using hibernate annotation
@Entity
@Table(name = "consumers")
public class Consumer {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private long id;
    @Column(name = "name")
    private String name;
    @Column(name = "address")
    private  String address;
    @Column(name = "phone")
    private  String phone;
//    @Column(name = "shipping_platform_id")
//    private  long shippingPlatformID;
    @JsonIgnore
    @OneToMany(mappedBy = "consumer", cascade = CascadeType.REMOVE, fetch = FetchType.LAZY)
    private Set<Order> orders;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "shopping_platform_id")
    private ShoppingPlatform shoppingPlatform;

    public  Consumer(){}

    public Set<Order> getOrders() {
        return orders;
    }
    public void setOrders(Set<Order> orders) {
        this.orders = orders;
    }
    public void setShoppingPlatform(ShoppingPlatform shoppingPlatform){
        this.shoppingPlatform = shoppingPlatform;
    }

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
    @Override
    public String toString(){
        return "{"+"id:"+getId()+", name:"+getName()+", address:"+getAddress()+", Phone:"+getPhone()+"}";
    }
    //    public long getShippingPlatformID() {
//        return shippi ngPlatformID;
//    }
//    public void setShippingPlatformID(long shippingPlatformID) {
//        this.shippingPlatformID = shippingPlatformID;
//    }
}
