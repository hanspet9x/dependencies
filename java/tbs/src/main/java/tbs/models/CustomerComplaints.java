package tbs.models;




public class CustomerComplaints {

	private String complaintDate;
	private String raisedBy;
	private String complaintCode;
	private String complaintRef;
	private String custno;
	private String custname;
	private String assignedTo;
	private String complaintStatus;
	private String agency;
	private String priority;
	private String acctPer;


	public String getComplaintDate() {
		return complaintDate;
	}

	public void setComplaintDate(String complaintDate) {
		this.complaintDate = complaintDate;
	}

	public String getRaisedBy() {
		return raisedBy;
	}

	public void setRaisedBy(String raisedBy) {
		this.raisedBy = raisedBy;
	}

	public String getComplaintCode() {
		return complaintCode;
	}

	public void setComplaintCode(String complaintCode) {
		this.complaintCode = complaintCode;
	}

	public String getComplaintRef() {
		return complaintRef;
	}

	public void setComplaintRef(String complaintRef) {
		this.complaintRef = complaintRef;
	}

	public String getCustno() {
		return custno;
	}

	public void setCustno(String custno) {
		this.custno = custno;
	}

	public String getCustname() {
		return custname;
	}

	public void setCustname(String custname) {
		this.custname = custname;
	}

	public String getAssignedTo() {
		return assignedTo;
	}

	public void setAssignedTo(String assignedTo) {
		this.assignedTo = assignedTo;
	}

	public String getComplaintStatus() {
		return complaintStatus;
	}

	public void setComplaintStatus(String complaintStatus) {
		this.complaintStatus = complaintStatus;
	}

	public String getAgency() {
		return agency;
	}

	public void setAgency(String agency) {
		this.agency = agency;
	}

	public String getPriority() {
		return priority;
	}

	public void setPriority(String priority) {
		this.priority = priority;
	}

	public String getAcctPer() {
		return acctPer;
	}

	public void setAcctPer(String acctPer) {
		this.acctPer = acctPer;
	}
}