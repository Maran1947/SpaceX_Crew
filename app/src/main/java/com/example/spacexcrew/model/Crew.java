package com.example.spacexcrew.model;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import com.google.gson.annotations.SerializedName;

@Entity(tableName = "crews")
public class Crew {

    @PrimaryKey(autoGenerate = true)
    private int crewId;

    @SerializedName("id")
    @ColumnInfo(name = "id")
    private String id;

    @SerializedName("name")
    @ColumnInfo(name = "name")
    private String name;

    public int getCrewId() {
        return crewId;
    }

    @SerializedName("agency")
    @ColumnInfo(name = "agency")
    private String agency;

    @SerializedName("status")
    @ColumnInfo(name = "status")
    private String status;

    @SerializedName("image")
    @ColumnInfo(name = "image")
    private String image;

    @SerializedName("wikipedia")
    @ColumnInfo(name = "wikipedia")
    private String wikipedia;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getAgency() {
        return agency;
    }

    public void setAgency(String agency) {
        this.agency = agency;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getWikipedia() {
        return wikipedia;
    }

    public void setWikipedia(String wikipedia) {
        this.wikipedia = wikipedia;
    }

    public Crew(String id, String name, String agency, String status, String image, String wikipedia, int crewId) {
        this.id = id;
        this.name = name;
        this.agency = agency;
        this.status = status;
        this.image = image;
        this.wikipedia = wikipedia;
        this.crewId = crewId;
    }
}
