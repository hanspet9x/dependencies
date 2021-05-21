package tbs.models;

import java.io.Serializable;

public class CustomerTypes implements Serializable {

    private int code;
    private String description;
    private String agency;


    public CustomerTypes() {
    }

    public String getAgency() {
        return agency;
    }

    public void setAgency(String agency) {
        this.agency = agency;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
