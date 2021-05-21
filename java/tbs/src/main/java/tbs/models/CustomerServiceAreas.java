package tbs.models;

import java.io.Serializable;

public class CustomerServiceAreas implements Serializable {
	private String description;
	private String shortName;
	private String agency;
	private int code;
	public CustomerServiceAreas() {
		super();
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
