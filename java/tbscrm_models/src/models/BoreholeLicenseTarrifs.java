package models;

import java.io.Serializable;

public class BoreholeLicenseTarrifs implements Serializable {
    private int id;
    private String itemCode;
    private String itemDesc;
    private double unitPrice;
    private String mgroup;
    private String agency;

    public BoreholeLicenseTarrifs() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getAgency() {
        return agency;
    }

    public void setAgency(String agency) {
        this.agency = agency;
    }

    public String getItemCode() {
        return itemCode;
    }

    public void setItemCode(String itemCode) {
        this.itemCode = itemCode;
    }

    public String getItemDesc() {
        return itemDesc;
    }

    public void setItemDesc(String itemDesc) {
        this.itemDesc = itemDesc;
    }

    public double getUnitPrice() {
        return unitPrice;
    }

    public void setUnitPrice(double unitPrice) {
        this.unitPrice = unitPrice;
    }

    public String getMgroup() {
        return mgroup;
    }

    public void setMgroup(String mgroup) {
        this.mgroup = mgroup;
    }
}
