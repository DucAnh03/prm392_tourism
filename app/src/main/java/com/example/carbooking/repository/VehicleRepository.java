package com.example.carbooking.repository;

import android.content.Context;

import com.example.carbooking.Entity.Vehicle;
import com.example.carbooking.dao.PRM392RoomDatabase;
import com.example.carbooking.dao.VehicleDao;

import java.util.List;

public class VehicleRepository {
    private VehicleDao vehicleDao;
    public  VehicleRepository(Context context){
        vehicleDao = PRM392RoomDatabase.getInstance(context).vehicleDao();
    }
    public void createVehicle(Vehicle vehicle){
        vehicleDao.insert(vehicle);
    }

    public void updateVehicle(Vehicle vehicle){
        vehicleDao.update(vehicle);
    }

    public Vehicle getVehicle(int vehicleId){
        return  vehicleDao.select(vehicleId);
    }

    public List<Vehicle> getAllVehicle(){
        return  vehicleDao.selectAll();
    }
}
