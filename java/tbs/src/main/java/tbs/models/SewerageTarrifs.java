package tbs.models;

import java.io.Serializable;

public class SewerageTarrifs implements Serializable {
	
	private String itemCode;
	private String itemDesc;
	private double unitPrice;
	private String mgroup;
	private String agency;
	
	
	public SewerageTarrifs() {
		super();
		// TODO Auto-generated constructor stub
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



	public String getAgency() {
		return agency;
	}


	public void setAgency(String agency) {
		this.agency = agency;
	}

	
	
}
