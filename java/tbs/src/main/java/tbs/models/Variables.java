package tbs.models;

import java.io.Serializable;
import java.util.List;


public class Variables implements Serializable {

    private String agency;
    private int id;
	private List<CustomerCategory> customerCategory;

    private List<BoreholeLicenseTarrifs> boreholeLicenseTarrifs;

    private List<CustomerTypes> customerTypes;

    private List<SubZones> subzones;

    private List<SubZones> street;

    private List<CustomerStreets> customerStreets;

    private List<MeteredWaterTarrifs> meteredWaterTarrifs;

    private List<UnmeteredWaterTarrifs> unmeteredWaterTarrifs;

    private List<Schemes> schemes;
    
    private List<ComplaintCodes> complaintCodes;
    
    private List<CustomerServiceAreas> customerServiceAreas;
    
    private List<SewerageTarrifs> sewerageTarrifs;
    
    

    public Variables() {
        // TODO Auto-generated constructor stub
    }

    public String getAgency() {
        return agency;
    }

    public void setAgency(String agency) {
        this.agency = agency;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public List<CustomerCategory> getCustomerCategory() {
        return customerCategory;
    }

    public void setCustomerCategory(List<CustomerCategory> customerCategory) {
        this.customerCategory = customerCategory;
    }

    public List<BoreholeLicenseTarrifs> getBoreholeLicenseTarrifs() {
        return boreholeLicenseTarrifs;
    }

    public void setBoreholeLicenseTarrifs(List<BoreholeLicenseTarrifs> boreholeLicenseTarrifs) {
        this.boreholeLicenseTarrifs = boreholeLicenseTarrifs;
    }

    public List<CustomerTypes> getCustomerTypes() {
        return customerTypes;
    }

    public void setCustomerTypes(List<CustomerTypes> customerTypes) {
        this.customerTypes = customerTypes;
    }

    public List<SubZones> getSubzones() {
        return subzones;
    }

    public void setSubzones(List<SubZones> subzones) {
        this.subzones = subzones;
    }

    public List<CustomerStreets> getCustomerStreets() {
        return customerStreets;
    }

    public void setCustomerStreets(List<CustomerStreets> customerStreets) {
        this.customerStreets = customerStreets;
    }

    public List<MeteredWaterTarrifs> getMeteredWaterTarrifs() {
        return meteredWaterTarrifs;
    }

    public void setMeteredWaterTarrifs(List<MeteredWaterTarrifs> meteredWaterTarrifs) {
        this.meteredWaterTarrifs = meteredWaterTarrifs;
    }

    public List<UnmeteredWaterTarrifs> getUnmeteredWaterTarrifs() {
        return unmeteredWaterTarrifs;
    }

    public void setUnmeteredWaterTarrifs(List<UnmeteredWaterTarrifs> unmeteredWaterTarrifs) {
        this.unmeteredWaterTarrifs = unmeteredWaterTarrifs;
    }

    public List<Schemes> getSchemes() {
        return schemes;
    }

    public void setSchemes(List<Schemes> schemes) {
        this.schemes = schemes;
    }

    public List<ComplaintCodes> getComplaintCodes() {
        return complaintCodes;
    }

    public void setComplaintCodes(List<ComplaintCodes> complaintCodes) {
        this.complaintCodes = complaintCodes;
    }

    public List<CustomerServiceAreas> getCustomerServiceAreas() {
        return customerServiceAreas;
    }

    public void setCustomerServiceAreas(List<CustomerServiceAreas> customerServiceAreas) {
        this.customerServiceAreas = customerServiceAreas;
    }

    public List<SewerageTarrifs> getSewerageTarrifs() {
        return sewerageTarrifs;
    }

    public void setSewerageTarrifs(List<SewerageTarrifs> sewerageTarrifs) {
        this.sewerageTarrifs = sewerageTarrifs;
    }

    public List<SubZones> getStreet() {
        return street;
    }

    public void setStreet(List<SubZones> street) {
        this.street = street;
    }
}
