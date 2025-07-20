package com.example.carbooking.dao;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import com.example.carbooking.Entity.Role;

import java.util.List;
@Dao
public interface RoleDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    void insert (Role role);

    @Update(onConflict = OnConflictStrategy.REPLACE)
    void update(Role role);

    @Query("SELECT * FROM Role r WHERE r.id =:role_id")
    Role select(int role_id);

    @Query("SELECT * FROM Role")
    List<Role> selectAll();

    @Query("DELETE FROM Role")
    void deleteAll();
}
