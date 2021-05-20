package tbs.models;

import java.io.Serializable;

public class HrMaster implements Serializable {


	private int id;
	private String emplNo;
	private String lName;
	private String fName;
	private String post;
	private String agency;

	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getEmplNo() {
		return emplNo;
	}
	public void setEmplNo(String emplNo) {
		this.emplNo = emplNo;
	}

	public String getlName() {
		return lName;
	}

	public void setlName(String lName) {
		this.lName = lName;
	}

	public String getAgency() {
		return agency;
	}
	public void setAgency(String agency) {
		this.agency = agency;
	}

	public String getfName() {
		return fName;
	}

	public void setfName(String fName) {
		this.fName = fName;
	}

	public String getPost() {
		return post;
	}

	public void setPost(String post) {
		this.post = post;
	}
}
