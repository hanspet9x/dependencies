package tbs.models;

public class Testi {

    private String idColumn;
    private String nameCount;
    private byte[] data;

    public Testi(String idColumn, String nameCount, byte[] data) {
        this.idColumn = idColumn;
        this.nameCount = nameCount;
        this.data = data;
    }

    public byte[] getData() {
        return data;
    }

    public void setData(byte[] data) {
        this.data = data;
    }

    public String getIdColumn() {
        return idColumn;
    }

    public void setIdColumn(String idColumn) {
        this.idColumn = idColumn;
    }

    public String getNameCount() {
        return nameCount;
    }

    public void setNameCount(String nameCount) {
        this.nameCount = nameCount;
    }
}
