package tbs.models;

public class Cashbook {
    private String docDt;
    private String custno;
    private String custname;
    private String invoiceNo;
    private String checkNo;
    private int income;
    private String details;
    private int acctPer;
    private String userid;
    private String agency;
    private String docRef;
    private String trNo;


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

    public String getDocRef() {
        return docRef;
    }

    public void setDocRef(String docRef) {
        this.docRef = docRef;
    }

    public String getTrNo() {
        return trNo;
    }

    public void setTrNo(String trNo) {
        this.trNo = trNo;
    }
}
