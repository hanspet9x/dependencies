package tbs.models;

public class Cashbook {
    private String docDt;
    private String custno;
    private String custname;
    private String invoiceNo;
    private String checkNo;
    private int income;
    private String details;
    private String acNo;
    private int acctPer;
    private String bankCode;
    private String userid;
    private String agency;

    public Cashbook() {
    }

    public String getDocDt() {
        return docDt;
    }

    public void setDocDt(String docDt) {
        this.docDt = docDt;
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

    public String getInvoiceNo() {
        return invoiceNo;
    }

    public void setInvoiceNo(String invoiceNo) {
        this.invoiceNo = invoiceNo;
    }

    public String getCheckNo() {
        return checkNo;
    }

    public void setCheckNo(String checkNo) {
        this.checkNo = checkNo;
    }

    public String getDetails() {
        return details;
    }

    public void setDetails(String details) {
        this.details = details;
    }

    public String getAcNo() {
        return acNo;
    }

    public void setAcNo(String acNo) {
        this.acNo = acNo;
    }

    public String getBankCode() {
        return bankCode;
    }

    public void setBankCode(String bankCode) {
        this.bankCode = bankCode;
    }

    public String getUserid() {
        return userid;
    }

    public void setUserid(String userid) {
        this.userid = userid;
    }

    public String getAgency() {
        return agency;
    }

    public void setAgency(String agency) {
        this.agency = agency;
    }

    public int getIncome() {
        return income;
    }

    public void setIncome(int income) {
        this.income = income;
    }

    public int getAcctPer() {
        return acctPer;
    }

    public void setAcctPer(int acctPer) {
        this.acctPer = acctPer;
    }
}
