package tbs.models;

public class Ocat {
     private int acctPer;
    private String domain;
    private int domainNo;
    private int score;
    private String subdomain;
    private double subdomainNo;
    private int weighted;
    private String agency;

    public Ocat() {
    }

    public int getAcctPer() {
        return acctPer;
    }

    public void setAcctPer(int acctPer) {
        this.acctPer = acctPer;
    }

    public String getDomain() {
        return domain;
    }

    public void setDomain(String domain) {
        this.domain = domain;
    }

    public int getDomainNo() {
        return domainNo;
    }

    public void setDomainNo(int domainNo) {
        this.domainNo = domainNo;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public String getSubdomain() {
        return subdomain;
    }

    public void setSubdomain(String subdomain) {
        this.subdomain = subdomain;
    }

    public double getSubdomainNo() {
        return subdomainNo;
    }

    public void setSubdomainNo(double subdomainNo) {
        this.subdomainNo = subdomainNo;
    }

    public int getWeighted() {
        return weighted;
    }

    public void setWeighted(int weighted) {
        this.weighted = weighted;
    }

    public String getAgency() {
        return agency;
    }

    public void setAgency(String agency) {
        this.agency = agency;
    }
}
