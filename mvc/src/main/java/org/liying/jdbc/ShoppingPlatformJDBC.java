package org.liying.jdbc;

public class ShoppingPlatformJDBC {
    public ShoppingPlatformJDBC(){}
    private long id;
    private String name;
    private String website;
    private String shippingMethod;

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
    public String toString() {
        return "ShoppingPlatformJDBC{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", website='" + website + '\'' +
                ", shippingMethod='" + shippingMethod + '\'' +
                '}';
    }
}

