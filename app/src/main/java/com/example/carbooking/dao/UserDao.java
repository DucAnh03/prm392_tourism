package com.example.carbooking.dao;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.example.carbooking.Entity.User;

import java.util.List;
@Dao
public interface UserDao {
    @Insert
    public void addUser(User user);
    @Update
    public void updateUser(User user);
    @Delete
    public void deleteUser(User user);
    @Query("select * from User")
    public List<User> getAllUser();
    @Query("select * from User where id==:user_id")
    public User getUser(int user_id);
    @Query("select * from User where username==:username and Password==:password")
    public User getUser(String username, String password);
    @Query("select * from User where username==:username")
    public User getUser(String username);
}
