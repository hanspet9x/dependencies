package tbs.models;

import java.io.Serializable;
import java.util.List;

public class CustomerEnumerations implements Serializable {
    private final List<Customers> customers;
    private final List<CustomersEnumeration> customersEnumerations;

    public CustomerEnumerations(List<Customers> customers, List<CustomersEnumeration> customersEnumerations) {
        this.customers = customers;
        this.customersEnumerations = customersEnumerations;
    }

    public List<Customers> getCustomers() {
        return customers;
    }

    public List<CustomersEnumeration> getEnumerations() {
        return customersEnumerations;
    }
}
