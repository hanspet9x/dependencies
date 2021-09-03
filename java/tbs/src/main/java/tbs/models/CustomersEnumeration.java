package tbs.models;

import java.io.Serializable;

public class CustomersEnumeration implements Serializable {
    private String custno;
    private String dpcCustno;
    private String zone;
    private String cc;
    private String address1;
    private String residentialUnit;
    private int numberOfRooms = 0;
    private String roundDesc;
    private int numberOfOccupants = 0;
    private int numberOfBlocks = 0;
    private int estimateWaterUsage = 0;
    private int noOfFloors = 0;
    private String lastEnumerated = "2000-01-01";
    private String sanitaryType;
    private String noOfBathrooms;
    private String otherSanitaryType;
    private String sanitaryDisposalMethod;

    public CustomersEnumeration() {
    }

    public String getCustno() {
        return custno;
    }

    public String getDpcCustno() {
        return dpcCustno;
    }

    public void setDpcCustno(String dpcCustno) {
        this.dpcCustno = dpcCustno;
    }

    public String getZone() {
        return zone;
    }

    public void setZone(String zone) {
        this.zone = zone;
    }

    public String getCc() {
        return cc;
    }

    public void setCc(String cc) {
        this.cc = cc;
    }

    public String getAddress1() {
        return address1;
    }

    public void setAddress1(String address1) {
        this.address1 = address1;
    }

    public void setCustno(String custno) {
        this.custno = custno;
    }

    public String getResidentialUnit() {
        return residentialUnit;
    }

    public void setResidentialUnit(String residentialUnit) {
        this.residentialUnit = residentialUnit;
    }

    public String getRoundDesc() {
        return roundDesc;
    }

    public void setRoundDesc(String roundDesc) {
        this.roundDesc = roundDesc;
    }

    public int getNumberOfBlocks() {
        return numberOfBlocks;
    }

    public void setNumberOfBlocks(int numberOfBlocks) {
        this.numberOfBlocks = numberOfBlocks;
    }

    public int getEstimateWaterUsage() {
        return estimateWaterUsage;
    }

    public int getNumberOfRooms() {
        return numberOfRooms;
    }

    public void setNumberOfRooms(int numberOfRooms) {
        this.numberOfRooms = numberOfRooms;
    }

    public int getNumberOfOccupants() {
        return numberOfOccupants;
    }

    public void setNumberOfOccupants(int numberOfOccupants) {
        this.numberOfOccupants = numberOfOccupants;
    }

    public void setEstimateWaterUsage(int estimateWaterUsage) {
        this.estimateWaterUsage = estimateWaterUsage;
    }

    public int getNoOfFloors() {
        return noOfFloors;
    }

    public void setNoOfFloors(int noOfFloors) {
        this.noOfFloors = noOfFloors;
    }

    public String getLastEnumerated() {
        return lastEnumerated;
    }

    public void setLastEnumerated(String lastEnumerated) {
        this.lastEnumerated = lastEnumerated;
    }

    public String getSanitaryType() {
        return sanitaryType;
    }

    public void setSanitaryType(String sanitaryType) {
        this.sanitaryType = sanitaryType;
    }

    public String getNoOfBathrooms() {
        return noOfBathrooms;
    }

    public void setNoOfBathrooms(String noOfBathrooms) {
        this.noOfBathrooms = noOfBathrooms;
    }

    public String getOtherSanitaryType() {
        return otherSanitaryType;
    }

    public void setOtherSanitaryType(String otherSanitaryType) {
        this.otherSanitaryType = otherSanitaryType;
    }

    public String getSanitaryDisposalMethod() {
        return sanitaryDisposalMethod;
    }

    public void setSanitaryDisposalMethod(String sanitaryDisposalMethod) {
        this.sanitaryDisposalMethod = sanitaryDisposalMethod;
    }
}
