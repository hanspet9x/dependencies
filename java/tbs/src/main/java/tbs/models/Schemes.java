package tbs.models;

import java.io.Serializable;

public class Schemes implements Serializable {

	private int sno;
	private String description;
	private String code;
	private String agency;
	private float longitude;
	private float latitude;


	public Schemes() {
		super();
		// TODO Auto-generated constructor stub
	}

	public float getLongitude() {
		return longitude;
	}

	public void setLongitude(float longitude) {
		this.longitude = longitude;
	}

	public float getLatitude() {
		return latitude;
	}

	public void setLatitude(float latitude) {
		this.latitude = latitude;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public int getSno() {
		return sno;
	}


	public void setSno(int sno) {
		this.sno = sno;
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

	
	
}
