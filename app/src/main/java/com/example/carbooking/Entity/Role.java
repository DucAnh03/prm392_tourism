package com.example.carbooking.Entity;


import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import org.jetbrains.annotations.NotNull;

@Entity(tableName = "Role")
public class Role {
    @PrimaryKey(autoGenerate = true)
    @NotNull
    @ColumnInfo(name = "id")
    private int Id;
    @NotNull
    @ColumnInfo(name = "RoleName")
    private int RoleName;

    public int getId() {
        return Id;
    }

    public void setId(int id) {
        Id = id;
    }

    public int getRoleName() {
        return RoleName;
    }

    public void setRoleName(int roleName) {
        RoleName = roleName;
    }
}
