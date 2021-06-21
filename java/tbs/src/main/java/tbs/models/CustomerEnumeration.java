package tbs.models;

import java.io.Serializable;

public class CustomerEnumeration implements Serializable {

    private Customers customers;
    private CustomersEnumeration customersEnumeration;

    public CustomerEnumeration(Customers customers, CustomersEnumeration customersEnumeration) {
        this.customers = customers;
        this.customersEnumeration = customersEnumeration;
    }

    public Customers getCustomer() {
        return customers;
    }

    public void setCustomer(Customers customers) {
        this.customers = customers;
    }

    public CustomersEnumeration getEnumeration() {
        return customersEnumeration;
    }

    public void setEnumeration(CustomersEnumeration customersEnumeration) {
        this.customersEnumeration = customersEnumeration;
    }
}
