package com.indiahacks.lisd;

/**
 * Created by adikundiv on 05-09-2017.
 */

public class Vehicle {

    int user_id,vehicle_id,vehicle_count;
    String vehicle_type;
    boolean immobilize;

    public Vehicle(int user_id, int vehicle_id, int vehicle_count, String vehicle_type, boolean immobilize) {
        this.user_id = user_id;
        this.vehicle_id = vehicle_id;
        this.vehicle_count = vehicle_count;
        this.vehicle_type = vehicle_type;
        this.immobilize = immobilize;
    }

    public int getUser_id() {
        return user_id;
    }

    public void setUser_id(int user_id) {
        this.user_id = user_id;
    }

    public int getVehicle_id() {
        return vehicle_id;
    }

    public void setVehicle_id(int vehicle_id) {
        this.vehicle_id = vehicle_id;
    }

    public int getVehicle_count() {
        return vehicle_count;
    }

    public void setVehicle_count(int vehicle_count) {
        this.vehicle_count = vehicle_count;
    }

    public String getVehicle_type() {
        return vehicle_type;
    }

    public void setVehicle_type(String vehicle_type) {
        this.vehicle_type = vehicle_type;
    }

    public boolean isImmobilize() {
        return immobilize;
    }

    public void setImmobilize(boolean immobilize) {
        this.immobilize = immobilize;
    }
}
