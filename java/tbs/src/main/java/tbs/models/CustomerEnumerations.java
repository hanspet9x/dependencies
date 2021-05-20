package tbs.models;

import java.io.Serializable;
import java.util.List;

public class CustomerEnumerations implements Serializable {
    private final List<Customer> customers;
    private final List<Enumeration> enumerations;

    public CustomerEnumerations(List<Customer> customers, List<Enumeration> enumerations) {
        this.customers = customers;
        this.enumerations = enumerations;
    }

    public List<Customer> getCustomers() {
        return customers;
    }

    public List<Enumeration> getEnumerations() {
        return enumerations;
    }
}
