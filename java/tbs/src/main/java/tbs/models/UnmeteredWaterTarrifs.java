package tbs.models;

import java.io.Serializable;

public class UnmeteredWaterTarrifs implements Serializable {

    private String itemCode;
    private String itemDesc;
    private double unitPrice;
    private String mgroup;
    private double usageRate1;
    private double usageBp1;
    private String agency;

    public UnmeteredWaterTarrifs() {
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

    public String getMgroup() {
        return mgroup;
    }

    public void setMgroup(String mgroup) {
        this.mgroup = mgroup;
    }

    public double getUnitPrice() {
        return unitPrice;
    }

    public void setUnitPrice(double unitPrice) {
        this.unitPrice = unitPrice;
    }

    public double getUsageRate1() {
        return usageRate1;
    }

    public void setUsageRate1(double usageRate1) {
        this.usageRate1 = usageRate1;
    }

    public double getUsageBp1() {
        return usageBp1;
    }

    public void setUsageBp1(double usageBp1) {
        this.usageBp1 = usageBp1;
    }
}
