package com.example.carbooking.dao;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;
import androidx.room.migration.Migration;
import androidx.sqlite.db.SupportSQLiteDatabase;

import com.example.carbooking.Entity.Category;
import com.example.carbooking.Entity.Converters;
import com.example.carbooking.Entity.Order;
import com.example.carbooking.Entity.Role;
import com.example.carbooking.Entity.Status;
import com.example.carbooking.Entity.Tour;
import com.example.carbooking.Entity.User;
import com.example.carbooking.Entity.Vehicle;

@Database(entities = {Category.class, Order.class,
        Role.class, Status.class, Tour.class, User.class, Vehicle.class}, version = 6)
@TypeConverters({Converters.class})
public abstract class PRM392RoomDatabase extends RoomDatabase {
    private static final String DB_NAME = "PRM392Database";
    private static PRM392RoomDatabase INSTANCE;

    public abstract CategoryDao categoryDao();
    public abstract UserDao userDao();
    public abstract OrderDao orderDao();
    public abstract RoleDao roleDao();
    public abstract StatusDao statusDao();
    public abstract TourDao tourDao();
    public abstract VehicleDao vehicleDao();

    public static synchronized PRM392RoomDatabase getInstance(Context context){
            if(INSTANCE == null){
                INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                                PRM392RoomDatabase.class, DB_NAME)
                        .allowMainThreadQueries()
                        .fallbackToDestructiveMigrationFrom(5)
                        .enableMultiInstanceInvalidation()
                        .build();
        }
        return INSTANCE;
    }
}
