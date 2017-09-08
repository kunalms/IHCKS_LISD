package com.indiahacks.lisd;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by adikundiv on 03-09-2017.
 */

public class Trip implements Serializable{

    private ArrayList<String> Latitude, Longitude,Timestamp;
    private int trip_id, vehicle_id ;
    boolean is_live;

    public Trip(ArrayList<String> latitude, ArrayList<String> longitude, ArrayList<String> timestamp, int trip_id, int vehicle_id,boolean is_live) {
        Latitude = latitude;
        Longitude = longitude;
        Timestamp = timestamp;
        this.trip_id = trip_id;
        this.vehicle_id = vehicle_id;
        this.is_live=is_live;
    }

    public ArrayList<String> getLatitude() {
        return Latitude;
    }

    public void setLatitude(ArrayList<String> latitude) {
        Latitude = latitude;
    }

    public ArrayList<String> getLongitude() {
        return Longitude;
    }

    public void setLongitude(ArrayList<String> longitude) {
        Longitude = longitude;
    }

    public ArrayList<String> getTimestamp() {
        return Timestamp;
    }

    public void setTimestamp(ArrayList<String> timestamp) {
        Timestamp = timestamp;
    }

    public int getTrip_id() {
        return trip_id;
    }

    public void setTrip_id(int trip_id) {
        this.trip_id = trip_id;
    }

    public int getVehicle_id() {
        return vehicle_id;
    }

    public void setVehicle_id(int vehicle_id) {
        this.vehicle_id = vehicle_id;
    }

    public String getInitialTime(){
        return Timestamp.get(0).toString();
    }

    public boolean is_live() {
        return is_live;
    }

    public void setIs_live(boolean is_live) {
        this.is_live = is_live;
    }
}
