package tbs.models;

public class Users {
    private String userid;
    private String username;
    private String agency;
    private String operationalUnit;
    private String emplNo;
    private String position;
    private String mobilePhone;
    private String email;
    private String employeeStatus;

    public Users() {
    }

    public String getUserid() {
        return userid;
    }

    public void setUserid(String userid) {
        this.userid = userid;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getAgency() {
        return agency;
    }

    public void setAgency(String agency) {
        this.agency = agency;
    }

    public String getOperationalUnit() {
        return operationalUnit;
    }

    public void setOperationalUnit(String operationalUnit) {
        this.operationalUnit = operationalUnit;
    }

    public String getEmplNo() {
        return emplNo;
    }

    public void setEmplNo(String emplNo) {
        this.emplNo = emplNo;
    }

    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position;
    }

    public String getMobilePhone() {
        return mobilePhone;
    }

    public void setMobilePhone(String mobilePhone) {
        this.mobilePhone = mobilePhone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getEmployeeStatus() {
        return employeeStatus;
    }

    public void setEmployeeStatus(String employeeStatus) {
        this.employeeStatus = employeeStatus;
    }
}
