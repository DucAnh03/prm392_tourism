package com.example.carbooking.dao;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import com.example.carbooking.Entity.Tour;

import java.util.List;
@Dao
public interface TourDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    void insert (Tour tour);

    @Update(onConflict = OnConflictStrategy.REPLACE)
    void update(Tour tour);

    @Query("SELECT * FROM Tour t WHERE t.id =:tour_id")
    Tour select(int tour_id);

    @Query("SELECT * FROM Tour")
    List<Tour> selectAll();

    @Query("DELETE FROM Tour")
    void deleteAll();
}
