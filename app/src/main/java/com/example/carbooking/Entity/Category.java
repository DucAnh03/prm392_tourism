package com.example.carbooking.Entity;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import org.jetbrains.annotations.NotNull;

@Entity(tableName = "Category")
public class Category {
    @PrimaryKey(autoGenerate = true)
    @NotNull
    @ColumnInfo(name = "id")
    private int id;
    @NotNull
    @ColumnInfo(name = "categoryName")
    private String categoryName;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @NotNull
    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(@NotNull String categoryName) {
        this.categoryName = categoryName;
    }
    public String toString() {
        return categoryName; // This makes the ArrayAdapter display the vehicle name
    }

    public Category(@NotNull String categoryName) {
        this.categoryName = categoryName;
    }
}
