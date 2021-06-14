package tbs.models;

public class PipelineLocations {
     private String city;
     private float endLat;
     private float endLong;
     private int pipeDiameter;
     private String pipelineCode;
     private String pipelineDesc;
     private float startLat;
     private float startLong;

    public PipelineLocations() {
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public float getEndLat() {
        return endLat;
    }

    public void setEndLat(float endLat) {
        this.endLat = endLat;
    }

    public float getEndLong() {
        return endLong;
    }

    public void setEndLong(float endLong) {
        this.endLong = endLong;
    }

    public int getPipeDiameter() {
        return pipeDiameter;
    }

    public void setPipeDiameter(int pipeDiameter) {
        this.pipeDiameter = pipeDiameter;
    }

    public String getPipelineCode() {
        return pipelineCode;
    }

    public void setPipelineCode(String pipelineCode) {
        this.pipelineCode = pipelineCode;
    }

    public String getPipelineDesc() {
        return pipelineDesc;
    }

    public void setPipelineDesc(String pipelineDesc) {
        this.pipelineDesc = pipelineDesc;
    }

    public float getStartLat() {
        return startLat;
    }

    public void setStartLat(float startLat) {
        this.startLat = startLat;
    }

    public float getStartLong() {
        return startLong;
    }

    public void setStartLong(float startLong) {
        this.startLong = startLong;
    }
}
