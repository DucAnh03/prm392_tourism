package com.example.carbooking.Entity;


import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.PrimaryKey;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@Entity(tableName = "User",
        foreignKeys = @ForeignKey(entity = Role.class, parentColumns = "id", childColumns = "RoleId"))
public class User {
    @PrimaryKey(autoGenerate = true)
    @NotNull
    @ColumnInfo(name = "id")
    private int id;

    @Nullable
    @ColumnInfo(name = "username")
    private String userName;

    @Nullable
    @ColumnInfo(name = "Password")
    private String password;

    @Nullable
    @ColumnInfo(name = "Email")
    private String email;
    @Nullable
    @ColumnInfo(name = "PhoneNumber")
    private String phoneNumber;

    @Nullable
    @ColumnInfo(name = "Address")
    private String Address;


    @ColumnInfo(name = "RoleId")
    private int Role_id;


    @ColumnInfo(name = "IsLocked")
    private boolean isLocked;
    @Nullable
    @ColumnInfo(name = "Avatar")
    private String avatar;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Nullable
    public String getUserName() {
        return userName;
    }

    public void setUserName(@Nullable String userName) {
        this.userName = userName;
    }

    @Nullable
    public String getPassword() {
        return password;
    }

    public void setPassword(@Nullable String password) {
        this.password = password;
    }

    @Nullable
    public String getEmail() {
        return email;
    }

    public void setEmail(@Nullable String email) {
        this.email = email;
    }

    @Nullable
    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(@Nullable String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    @Nullable
    public String getAddress() {
        return Address;
    }

    public void setAddress(@Nullable String address) {
        Address = address;
    }

    public int getRole_id() {
        return Role_id;
    }

    public void setRole_id(int role_id) {
        Role_id = role_id;
    }

    public boolean isLocked() {
        return isLocked;
    }

    public void setLocked(boolean locked) {
        isLocked = locked;
    }

    @Nullable
    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(@Nullable String avatar) {
        this.avatar = avatar;
    }
}
