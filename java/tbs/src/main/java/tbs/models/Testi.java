package tbs.models;

public class Testi {

    private String auto;
    private String datem;

    public Testi(String name, String datem) {
        this.auto = name;
        this.datem = datem;
    }

    public String getDatem() {
        return datem;
    }

    public void setDatem(String datem) {
        this.datem = datem;
    }

    public Testi() {
    }

    public String getAuto() {
        return auto;
    }

    public void setAuto(String name) {
        this.auto = name;
    }
}
