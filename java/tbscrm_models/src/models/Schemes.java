package models;

import java.io.Serializable;

public class Schemes implements Serializable {

	private int id;
	private int sno;
	private String description;
	private String code;
	private String agency;
	
	public Schemes() {
		super();
		// TODO Auto-generated constructor stub
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
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
