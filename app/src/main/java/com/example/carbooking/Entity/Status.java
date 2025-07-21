package com.example.carbooking.Entity;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import org.jetbrains.annotations.NotNull;

@Entity(tableName = "Status")
public class Status {
    @PrimaryKey
    @NotNull
    @ColumnInfo(name = "id")
    private int id;
    @NotNull
    @ColumnInfo(name = "statusName")
    private String statusName;
    @NotNull
    @ColumnInfo(name = "statusDesc")
    private String statusDesc;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @NotNull
    public String getStatusName() {
        return statusName;
    }

    public void setStatusName(@NotNull String statusName) {
        this.statusName = statusName;
    }

    @NotNull
    public String getStatusDesc() {
        return statusDesc;
    }

    public void setStatusDesc(@NotNull String statusDesc) {
        this.statusDesc = statusDesc;
    }


}
