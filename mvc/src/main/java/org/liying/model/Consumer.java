package org.liying.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import javax.persistence.*;
import java.util.Objects;
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


    // consumer side
    @JsonIgnore
    @OneToMany(mappedBy = "consumer", cascade = CascadeType.REMOVE, fetch = FetchType.LAZY)
    private Set<Order> orders;

    // sp
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "shopping_platform_id") // FK: sp's pk as consumer's fk
    private ShoppingPlatform shoppingPlatform;
//    @Column(name = "shipping_platform_id")
//    private  long shippi ngPlatformID;

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

    @Override
    public int hashCode() {
        return Objects.hash(id,name,address,phone); // key-value??
    }



    @Override
    public boolean equals(Object o){
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Consumer consumer = (Consumer) o; // 强转
        return id == consumer.id &&
                name.equals(consumer.name) &&
                Objects.equals(address, consumer.address) &&
                Objects.equals(phone, consumer.phone);
    }
//    public long getShippingPlatformID() {
//        return shippingPlatformID;
//    }
//    public void setShippingPlatformID(long shippingPlatformID) {
//        this.shippingPlatformID = shippingPlatformID;
//    }
}
