package se.waterwise.APIs;

public class GroundWaterData {

    private int areaId;
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