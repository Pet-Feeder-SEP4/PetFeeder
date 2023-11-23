package com.example.petfeedercloud.dtos;

import com.example.petfeedercloud.models.Pet;
import com.example.petfeedercloud.models.Schedule;
import com.example.petfeedercloud.models.UserP;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;

import java.util.Date;

public class PetFeederDTO {
    private Long petFeederId;
    private String petFeederLabel;
    private String foodLevel;
    private String lowLevelFood;
    private String foodHumidity;
    private String waterTemperture;
    private Long userId;
    private Long scheduleId;
    private Long petId;

    public Long getPetFeederId() {
        return petFeederId;
    }

    public void setPetFeederId(Long petFeederId) {
        this.petFeederId = petFeederId;
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

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Long getScheduleId() {
        return scheduleId;
    }

    public void setScheduleId(Long scheduleId) {
        this.scheduleId = scheduleId;
    }

    public Long getPetId() {
        return petId;
    }

    public void setPetId(Long petId) {
        this.petId = petId;
    }
}
