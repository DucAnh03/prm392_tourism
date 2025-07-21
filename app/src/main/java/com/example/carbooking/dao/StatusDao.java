package com.example.carbooking.dao;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import com.example.carbooking.Entity.Role;
import com.example.carbooking.Entity.Status;

import java.util.List;
@Dao
public interface StatusDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    void insert (Status status);

    @Update(onConflict = OnConflictStrategy.REPLACE)
    void update(Status status);

    @Query("SELECT * FROM Status s WHERE s.id =:id")
    Status select(int id);

    @Query("SELECT * FROM Status")
    List<Status> selectAll();

    @Query("DELETE FROM Status")
    void deleteAll();
}
