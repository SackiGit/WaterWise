package se.waterwise.APIs.meteo;

public class RainData {

    private String date;
    private double rain;

    public RainData (String date, double rain){
        this.date = date;
        this.rain = rain;
    }

    public String getDate() {
        return date;
    }

    public double getRain() {
        return rain;
    }

}
