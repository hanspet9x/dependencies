package tbs.models;

import java.io.Serializable;

public class HrMaster implements Serializable {


	private String emplNo;
	private String emplName;
	private String lName;
	private String fName;
	private String post;
	private String agency;
	private String department;


	public String getEmplNo() {
		return emplNo;
	}

	public String getDepartment() {
		return department;
	}

	public void setDepartment(String department) {
		this.department = department;
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

	public String getEmplName() {
		return emplName;
	}

	public void setEmplName(String emplName) {
		this.emplName = emplName;
	}
}
