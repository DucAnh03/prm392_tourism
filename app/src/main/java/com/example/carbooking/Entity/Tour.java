package com.example.carbooking.Entity;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.io.Serializable;
import java.util.Date;
import java.util.Objects;

@Entity(tableName = "Tour",
        foreignKeys = {
                @ForeignKey(entity = Category.class, parentColumns = "id", childColumns = "categoryId"),
                @ForeignKey(entity = Vehicle.class, parentColumns = "id", childColumns = "vehicle")
        })
public class Tour implements Serializable {
    @NotNull
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    private int id;
    @NotNull
    @ColumnInfo(name = "tile")
    private String tile;
    @NotNull
    @ColumnInfo(name = "locationFrom")
    private String locationFrom;
    @NotNull
    @ColumnInfo(name = "locationTo")
    private String locationTo;
    @NotNull
    @ColumnInfo(name = "tourTime")
    private String tourTime;
    @NotNull
    @ColumnInfo(name = "dateNumber")
    private int dateNumber;
    @NotNull
    @ColumnInfo(name = "description")
    private String description;
    @NotNull
    @ColumnInfo(name = "tourSchdule")
    private String tourSchdule;
    @NotNull
    @ColumnInfo(name = "pricePerPerson")
    private double pricePerPerson;
    @NotNull
    @ColumnInfo(name = "vehicle")
    private int vehicle;
    @NotNull
    @ColumnInfo(name = "categoryId")
    private int categoryId;
    @NotNull
    @ColumnInfo(name = "votedNumber")
    private int votedNumber;
    @NotNull
    @ColumnInfo(name = "voteScore")
    //note
    private int voteScore;
    @NotNull
    @ColumnInfo(name = "isAvaliable")
    private boolean isAvaliable;
    @NotNull
    @ColumnInfo(name = "contactNumber")
    private String contactNumber;

    @Nullable
    @ColumnInfo(name = "image")
    private String image;
    public Tour(){

    }
    @Ignore
    public Tour( @NotNull String tile, @NotNull String locationFrom, @NotNull String locationTo, @NotNull String tourTime, int dateNumber, @NotNull String description, @NotNull String tourSchdule, double pricePerPerson, int vehicle, int categoryId, int votedNumber, int voteScore, boolean isAvaliable, @NotNull String contactNumber, String image) {
        this.tile = tile;
        this.locationFrom = locationFrom;
        this.locationTo = locationTo;
        this.tourTime = tourTime;
        this.dateNumber = dateNumber;
        this.description = description;
        this.tourSchdule = tourSchdule;
        this.pricePerPerson = pricePerPerson;
        this.vehicle = vehicle;
        this.categoryId = categoryId;
        this.votedNumber = votedNumber;
        this.voteScore = voteScore;
        this.isAvaliable = isAvaliable;
        this.contactNumber = contactNumber;
        this.image = image;
    }
    @Ignore
    public Tour(@NotNull int id,@NotNull String tile, @NotNull String locationFrom, @NotNull String locationTo, @NotNull String tourTime, int dateNumber, @NotNull String description, @NotNull String tourSchdule, double pricePerPerson, int vehicle, int categoryId, int votedNumber, int voteScore, boolean isAvaliable, @NotNull String contactNumber,String image) {
        this.id = id;
        this.tile = tile;
        this.locationFrom = locationFrom;
        this.locationTo = locationTo;
        this.tourTime = tourTime;
        this.dateNumber = dateNumber;
        this.description = description;
        this.tourSchdule = tourSchdule;
        this.pricePerPerson = pricePerPerson;
        this.vehicle = vehicle;
        this.categoryId = categoryId;
        this.votedNumber = votedNumber;
        this.voteScore = voteScore;
        this.isAvaliable = isAvaliable;
        this.contactNumber = contactNumber;
        this.image = image;
    }



    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @NotNull
    public String getTile() {
        return tile;
    }

    public void setTile(@NotNull String tile) {
        this.tile = tile;
    }

    @NotNull
    public String getLocationFrom() {
        return locationFrom;
    }

    public void setLocationFrom(@NotNull String locationFrom) {
        this.locationFrom = locationFrom;
    }

    @NotNull
    public String getLocationTo() {
        return locationTo;
    }

    public void setLocationTo(@NotNull String locationTo) {
        this.locationTo = locationTo;
    }

    @NotNull
    public String getTourTime() {
        return tourTime;
    }

    public void setTourTime(@NotNull String tourTime) {
        this.tourTime = tourTime;
    }

    @NotNull
    public int getDateNumber() {
        return dateNumber;
    }

    public void setDateNumber(@NotNull int dateNumber) {
        this.dateNumber = dateNumber;
    }

    @NotNull
    public String getDescription() {
        return description;
    }

    public void setDescription(@NotNull String description) {
        this.description = description;
    }

    @NotNull
    public String getTourSchdule() {
        return tourSchdule;
    }

    public void setTourSchdule(@NotNull String tourSchdule) {
        this.tourSchdule = tourSchdule;
    }

    public double getPricePerPerson() {
        return pricePerPerson;
    }

    public void setPricePerPerson(double pricePerPerson) {
        this.pricePerPerson = pricePerPerson;
    }

    @NotNull
    public int getVehicle() {
        return vehicle;
    }

    public void setVehicle(@NotNull int vehicle) {
        this.vehicle = vehicle;
    }

    public int getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(int categoryId) {
        this.categoryId = categoryId;
    }

    public int getVotedNumber() {
        return votedNumber;
    }

    public void setVotedNumber(int votedNumber) {
        this.votedNumber = votedNumber;
    }

    public int getVoteScore() {
        return voteScore;
    }

    public void setVoteScore(int voteScore) {
        this.voteScore = voteScore;
    }

    public boolean isAvaliable() {
        return isAvaliable;
    }

    public void setAvaliable(boolean avaliable) {
        isAvaliable = avaliable;
    }

    @NotNull
    public String getContactNumber() {
        return contactNumber;
    }

    public void setContactNumber(@NotNull String contactNumber) {
        this.contactNumber = contactNumber;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Tour tour = (Tour) o;
        return id == tour.id && dateNumber == tour.dateNumber && Double.compare(pricePerPerson, tour.pricePerPerson) == 0 && vehicle == tour.vehicle && categoryId == tour.categoryId && votedNumber == tour.votedNumber && voteScore == tour.voteScore && isAvaliable == tour.isAvaliable && Objects.equals(tile, tour.tile) && Objects.equals(locationFrom, tour.locationFrom) && Objects.equals(locationTo, tour.locationTo) && Objects.equals(tourTime, tour.tourTime) && Objects.equals(description, tour.description) && Objects.equals(tourSchdule, tour.tourSchdule) && Objects.equals(contactNumber, tour.contactNumber) && Objects.equals(image, tour.image);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, tile, locationFrom, locationTo, tourTime, dateNumber, description, tourSchdule, pricePerPerson, vehicle, categoryId, votedNumber, voteScore, isAvaliable, contactNumber, image);
    }
}
