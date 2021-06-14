package tbs.models;

public class WaterLeaksDetection {

    private String  assignedTo;
    private String  cc;
    private double  lastDocno;
    private String  lastSurveyDate;
    private byte[]  leakPicture = "".getBytes();
    private String  leakingEquipmentUsed;
    private int  lengthOfPipe;
    private String  narration;
    private int  numberLeaksFound;
    private int  numberLeaksRepaired;
    private String  pipeDescription;
    private String  surveyDate;
    private String leakPictureBase;
    private String  zone;
    private String agency;

    public WaterLeaksDetection() {
    }

    public String getAssignedTo() {
        return assignedTo;
    }

    public void setAssignedTo(String assignedTo) {
        this.assignedTo = assignedTo;
    }

    public String getCc() {
        return cc;
    }

    public void setCc(String cc) {
        this.cc = cc;
    }

    public double getLastDocno() {
        return lastDocno;
    }

    public void setLastDocno(double lastDocno) {
        this.lastDocno = lastDocno;
    }

    public String getLastSurveyDate() {
        return lastSurveyDate;
    }

    public void setLastSurveyDate(String lastSurveyDate) {
        this.lastSurveyDate = lastSurveyDate;
    }

    public byte[] getLeakPicture() {
        return leakPicture;
    }

    public void setLeakPicture(byte[] leakPicture) {
        this.leakPicture = leakPicture;
    }

    public String getLeakingEquipmentUsed() {
        return leakingEquipmentUsed;
    }

    public void setLeakingEquipmentUsed(String leakingEquipmentUsed) {
        this.leakingEquipmentUsed = leakingEquipmentUsed;
    }

    public int getLengthOfPipe() {
        return lengthOfPipe;
    }

    public void setLengthOfPipe(int lengthOfPipe) {
        this.lengthOfPipe = lengthOfPipe;
    }

    public String getNarration() {
        return narration;
    }

    public void setNarration(String narration) {
        this.narration = narration;
    }

    public int getNumberLeaksFound() {
        return numberLeaksFound;
    }

    public void setNumberLeaksFound(int numberLeaksFound) {
        this.numberLeaksFound = numberLeaksFound;
    }

    public int getNumberLeaksRepaired() {
        return numberLeaksRepaired;
    }

    public void setNumberLeaksRepaired(int numberLeaksRepaired) {
        this.numberLeaksRepaired = numberLeaksRepaired;
    }

    public String getPipeDescription() {
        return pipeDescription;
    }

    public void setPipeDescription(String pipeDescription) {
        this.pipeDescription = pipeDescription;
    }

    public String getSurveyDate() {
        return surveyDate;
    }

    public void setSurveyDate(String surveyDate) {
        this.surveyDate = surveyDate;
    }

    public String getZone() {
        return zone;
    }

    public void setZone(String zone) {
        this.zone = zone;
    }

    public String getAgency() {
        return agency;
    }

    public void setAgency(String agency) {
        this.agency = agency;
    }

    public String getLeakPictureBase() {
        return leakPictureBase;
    }

    public void setLeakPictureBase(String leakPictureBase) {
        this.leakPictureBase = leakPictureBase;
    }
}
