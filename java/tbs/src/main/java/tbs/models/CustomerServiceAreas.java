package tbs.models;

import java.io.Serializable;

public class CustomerServiceAreas implements Serializable {
	private int id;
	private String description;
	private String shortName;
	private String agency;
	
	public CustomerServiceAreas() {
		super();
		// TODO Auto-generated constructor stub
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
	public String getShortName() {
		return shortName;
	}
	public void setShortName(String shortName) {
		this.shortName = shortName;
	}
	public String getAgency() {
		return agency;
	}
	public void setAgency(String agency) {
		this.agency = agency;
	}
	
	
	
	
}
