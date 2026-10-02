package se.waterwise.APIs.sgu;

public class GroundWaterData {

    // Unique code for 4*4 km area
    private int areaId;
    // Date of last estimated ground water level
    private String date;
    private int level;

    public GroundWaterData(int areaId,String date, int level) {
        this.areaId = areaId;
        this.date = date;
        this.level = level;
    }

    public int getAreaId(){
        return  areaId;
    }

    public String getDate() {
        return date;
    }

    public int getLevel() {
        return level;
    }
}