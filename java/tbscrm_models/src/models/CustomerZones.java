package models;

import java.io.Serializable;

public class CustomerZones implements Serializable {

    private int id;
    private String description;
    private String shortName;
    private int lastCustomerNo;
    private String agency;


    public CustomerZones() {

    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getAgency() {
        return agency;
    }

    public void setAgency(String agency) {
        this.agency = agency;
    }

    public int getLastCustomerNo() {
        return lastCustomerNo;
    }

    public void setLastCustomerNo(int lastCustomerNo) {
        this.lastCustomerNo = lastCustomerNo;
    }

    public String getShortName() {
        return shortName;
    }

    public void setShortName(String shortName) {
        this.shortName = shortName;
    }
}
