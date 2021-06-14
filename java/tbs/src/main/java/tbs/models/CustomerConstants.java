package tbs.models;

import java.util.List;

public class CustomerConstants {
    private List<ComplaintCodes> complaintCodes;
    private List<CustomerCategory> categories;
    private List<CustomerTypes> types;
    private List<CustomerServiceAreas> serviceAreas;
    private List<SubZones> subZones;
    private List<CustomerStreets> streets;
    private List<CustomerSubacctCategories> subacctCategories;
    private List<Schemes> schemes;

    public CustomerConstants(List<ComplaintCodes> complaintCodes, List<CustomerCategory> categories, List<CustomerTypes> types, List<CustomerServiceAreas> serviceAreas, List<SubZones> subZones, List<CustomerStreets> streets, List<CustomerSubacctCategories> subacctCategories, List<Schemes> schemes) {
        this.complaintCodes = complaintCodes;
        this.categories = categories;
        this.types = types;
        this.serviceAreas = serviceAreas;
        this.subZones = subZones;
        this.streets = streets;
        this.subacctCategories = subacctCategories;
        this.schemes = schemes;
    }

    public CustomerConstants() {
    }

    public List<ComplaintCodes> getComplaintCodes() {
        return complaintCodes;
    }

    public void setComplaintCodes(List<ComplaintCodes> complaintCodes) {
        this.complaintCodes = complaintCodes;
    }

    public List<CustomerCategory> getCategories() {
        return categories;
    }

    public void setCategories(List<CustomerCategory> categories) {
        this.categories = categories;
    }

    public List<CustomerTypes> getTypes() {
        return types;
    }

    public void setTypes(List<CustomerTypes> types) {
        this.types = types;
    }

    public List<CustomerServiceAreas> getServiceAreas() {
        return serviceAreas;
    }

    public void setServiceAreas(List<CustomerServiceAreas> serviceAreas) {
        this.serviceAreas = serviceAreas;
    }

    public List<SubZones> getSubZones() {
        return subZones;
    }

    public void setSubZones(List<SubZones> subZones) {
        this.subZones = subZones;
    }

    public List<CustomerStreets> getStreets() {
        return streets;
    }

    public void setStreets(List<CustomerStreets> streets) {
        this.streets = streets;
    }

    public List<CustomerSubacctCategories> getSubacctCategories() {
        return subacctCategories;
    }

    public void setSubacctCategories(List<CustomerSubacctCategories> subacctCategories) {
        this.subacctCategories = subacctCategories;
    }

    public List<Schemes> getSchemes() {
        return schemes;
    }

    public void setSchemes(List<Schemes> schemes) {
        this.schemes = schemes;
    }
}
