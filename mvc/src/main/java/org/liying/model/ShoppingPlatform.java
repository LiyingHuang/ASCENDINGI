//package org.liying.model;
//
//public class ShoppingPlatform {
//    public ShoppingPlatform(){}
//
//    private long id;
//    private String name;
//    private String website;
//    private String shippingMethod;
//
//    public long getId() {
//        return id;
//    }
//
//    public void setId(long id) {
//        this.id = id;
//    }
//
//    public String getName() {
//        return name;
//    }
//
//    public void setName(String name) {
//        this.name = name;
//    }
//
//    public String getWebsite() {
//        return website;
//    }
//
//    public void setWebsite(String website) {
//        this.website = website;
//    }
//
//    public String getShippingMethod() {
//        return shippingMethod;
//    }
//
//    public void setShippingMethod(String shippingMethod) {
//        this.shippingMethod = shippingMethod;
//    }
//}
package org.liying.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonView;

import javax.persistence.*;
import java.util.List;
import java.util.Set;

@Entity
@Table(name = "shopping_platforms")
public class ShoppingPlatform {

    //public static class ExtendedView extends BasicView{}
    //public static class BasicView{}
    public ShoppingPlatform(){}

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    //@JsonView(BasicView.class)
    private long id;

    @Column(name = "name")
    //@JsonView(BasicView.class)
    private String name;
    @Column(name = "website")
    //@JsonView(BasicView.class)
    private String website;
    @Column(name = "shipping_method")
    //@JsonView(BasicView.class)
    private String shippingMethod;

    @OneToMany(mappedBy = "shoppingPlatform", cascade = CascadeType.REMOVE, fetch = FetchType.LAZY)
    @JsonIgnore
    //@JsonView(ExtendedView.class)
    private Set<Consumer> consumers;

    public Set<Consumer> getConsumers(){
        return this.consumers = consumers;
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
    public String getWebsite() {
        return website;
    }
    public void setWebsite(String website) {
        this.website = website;
    }
    public String getShippingMethod() {
        return shippingMethod;
    }
    public void setShippingMethod(String shippingMethod) {
        this.shippingMethod = shippingMethod;
    }

    @Override
    public String toString(){
        return "{"+"id:"+getId()+", name:"+getName()+", website:"+getWebsite()+", ShippingMethod:"+getShippingMethod()+"}";
    }
}
