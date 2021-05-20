package tbs.models;

public class ComplaintsFileMovement {
    
    String submittedToName;
    String submittedToEmplNo;
    String submittedTo;
    String lastComment;
    String forwardedByEmplNo;
    String forwardedBy;
    String forwardByUserid;
    String dateForwarded;
    String complaintStatus;
    String complaintRef;
    String agency;

    public ComplaintsFileMovement() {

    }

    public String getSubmittedToName() {
        return submittedToName;
    }

    public void setSubmittedToName(String submittedToName) {
        this.submittedToName = submittedToName;
    }

    public String getSubmittedToEmplNo() {
        return submittedToEmplNo;
    }

    public void setSubmittedToEmplNo(String submittedToEmplNo) {
        this.submittedToEmplNo = submittedToEmplNo;
    }

    public String getSubmittedTo() {
        return submittedTo;
    }

    public void setSubmittedTo(String submittedTo) {
        this.submittedTo = submittedTo;
    }

    public String getLastComment() {
        return lastComment;
    }

    public void setLastComment(String lastComment) {
        this.lastComment = lastComment;
    }

    public String getForwardedByEmplNo() {
        return forwardedByEmplNo;
    }

    public void setForwardedByEmplNo(String forwardedByEmplNo) {
        this.forwardedByEmplNo = forwardedByEmplNo;
    }

    public String getForwardedBy() {
        return forwardedBy;
    }

    public void setForwardedBy(String forwardedBy) {
        this.forwardedBy = forwardedBy;
    }

    public String getForwardByUserid() {
        return forwardByUserid;
    }

    public void setForwardByUserid(String forwardByUserid) {
        this.forwardByUserid = forwardByUserid;
    }

    public String getDateForwarded() {
        return dateForwarded;
    }

    public void setDateForwarded(String dateForwarded) {
        this.dateForwarded = dateForwarded;
    }

    public String getComplaintStatus() {
        return complaintStatus;
    }

    public void setComplaintStatus(String complaintStatus) {
        this.complaintStatus = complaintStatus;
    }

    public String getComplaintRef() {
        return complaintRef;
    }

    public void setComplaintRef(String complaintRef) {
        this.complaintRef = complaintRef;
    }

    public String getAgency() {
        return agency;
    }

    public void setAgency(String agency) {
        this.agency = agency;
    }
}
