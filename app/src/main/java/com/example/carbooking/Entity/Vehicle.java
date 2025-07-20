package com.example.carbooking.Entity;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import org.jetbrains.annotations.NotNull;

@Entity(tableName = "Vehicle")
public class Vehicle {
    @PrimaryKey(autoGenerate = true)
    @NotNull
    @ColumnInfo(name = "id")
    private int id;
    @ColumnInfo(name = "vehicleName")
    @NotNull
    private String vehicleName;

    public Vehicle(String vehicleName) {
        this.vehicleName = vehicleName;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @NotNull
    public String getVehicleName() {
        return vehicleName;
    }

    public void setVehicleName(@NotNull String vehicleName) {
        this.vehicleName = vehicleName;
    }
    @Override
    public String toString() {
        return vehicleName; // This makes the ArrayAdapter display the vehicle name
    }
}
