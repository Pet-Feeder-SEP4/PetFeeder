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
    private int foodLevel;

    private int foodHumidity;
    private int waterTemperture;
    private int waterLevel;
    private Long userId;
    private Long scheduleId;
    private Long petId;
    private boolean active;
    private boolean connected;
    public int getWaterLevel() {
        return waterLevel;
    }
    public void setWaterLevel(int waterLevel) {
        this.waterLevel = waterLevel;
    }
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

    public int getFoodLevel() {
        return foodLevel;
    }

    public void setFoodLevel(int foodLevel) {
        this.foodLevel = foodLevel;
    }


    public int getFoodHumidity() {
        return foodHumidity;
    }

    public void setFoodHumidity(int foodHumidity) {
        this.foodHumidity = foodHumidity;
    }

    public int getWaterTemperture() {
        return waterTemperture;
    }

    public void setWaterTemperture(int waterTemperture) {
        this.waterTemperture = waterTemperture;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Long getScheduleId() {return scheduleId;}

    public void setScheduleId(Long scheduleId) {this.scheduleId = scheduleId;}

    public Long getPetId() {
        return petId;
    }

    public void setPetId(Long petId) {
        this.petId = petId;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }


    public boolean isConnected() {
        return connected;
    }

    public void setConnected(boolean connected) {
        this.connected = connected;
    }
}
