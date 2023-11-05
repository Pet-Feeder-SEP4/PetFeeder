package com.example.petfeedercloud.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;

import java.util.List;

@Entity
public class PetFeeder {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long petFeederId;

    private Long scheduleId;

    private Long userId;

    public Long petId;

    private String petFeederLabel;
    private String foodLevel;
    private String lowLevelFood;
    private String foodHumidity;
    private String waterTemperture;

    public PetFeeder() {

    }

    public Long getScheduleId() {
        return scheduleId;
    }

    public void setSchedule(Long scheduleId) {
        this.scheduleId = scheduleId;
    }

    public long getUser() {
        return userId;
    }

    public void setUser(Long userId) {
        this.userId = userId;
    }

    public Long getPetId() {
        return petId;
    }

    public void setPetId(Long petId) {
        this.petId = petId;
    }

    public String getPetFeederLabel() {
        return petFeederLabel;
    }

    public void setPetFeederLabel(String petFeederLabel) {
        this.petFeederLabel = petFeederLabel;
    }

    public String getFoodLevel() {
        return foodLevel;
    }

    public void setFoodLevel(String foodLevel) {
        this.foodLevel = foodLevel;
    }

    public String getLowLevelFood() {
        return lowLevelFood;
    }

    public void setLowLevelFood(String lowLevelFood) {
        this.lowLevelFood = lowLevelFood;
    }

    public String getFoodHumidity() {
        return foodHumidity;
    }

    public void setFoodHumidity(String foodHumidity) {
        this.foodHumidity = foodHumidity;
    }

    public String getWaterTemperture() {
        return waterTemperture;
    }

    public void setWaterTemperture(String waterTemperture) {
        this.waterTemperture = waterTemperture;
    }

    public void setPetFeederId(Long petFeederId) {
        this.petFeederId = petFeederId;
    }

    public Long getPetFeederId() {
        return petFeederId;
    }
}
