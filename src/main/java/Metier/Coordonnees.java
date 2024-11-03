package Metier;

import java.io.Serializable;

public class Coordonnees implements Serializable {
    private double latitude1;
    private double altitude1;
    private double latitude2;
    private double altitude2;

    public Coordonnees(double latitude1, double altitude1, double latitude2, double altitude2) {
        this.latitude1 = latitude1;
        this.altitude1 = altitude1;
        this.latitude2 = latitude2;
        this.altitude2 = altitude2;
    }

    public double getLatitude1() {
        return latitude1;
    }

    public void setLatitude1(double latitude1) {
        this.latitude1 = latitude1;
    }

    public double getAltitude1() {
        return altitude1;
    }

    public void setAltitude1(double altitude1) {
        this.altitude1 = altitude1;
    }

    public double getLatitude2() {
        return latitude2;
    }

    public void setLatitude2(double latitude2) {
        this.latitude2 = latitude2;
    }

    public double getAltitude2() {
        return altitude2;
    }

    public void setAltitude2(double altitude2) {
        this.altitude2 = altitude2;
    }

    @Override
    public String toString() {
        return "Coordonnees{" +
                "latitude1=" + latitude1 +
                ", altitude1=" + altitude1 +
                ", latitude2=" + latitude2 +
                ", altitude2=" + altitude2 +
                '}';
    }
}
