package tbs.models;

import java.io.Serializable;

public class CustomerEnumeration implements Serializable {

    private Customer customer;
    private Enumeration enumeration;

    public CustomerEnumeration(Customer customer, Enumeration enumeration) {
        this.customer = customer;
        this.enumeration = enumeration;
    }

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    public Enumeration getEnumeration() {
        return enumeration;
    }

    public void setEnumeration(Enumeration enumeration) {
        this.enumeration = enumeration;
    }
}
