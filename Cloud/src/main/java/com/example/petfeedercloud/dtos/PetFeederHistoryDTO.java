package com.example.petfeedercloud.dtos;

import com.example.petfeedercloud.models.PetFeeder;
import jakarta.persistence.*;

import java.time.LocalDate;
import java.time.LocalTime;

public class PetFeederHistoryDTO {
    private Long petFeederHistoryId;
    private Long petFeederId;
    private LocalDate date;
    private LocalTime time;
    private int foodLevel;
    private int foodHumidity;
    private int waterTemperature;

    public Long getPetFeederHistoryId() {
        return petFeederHistoryId;
    }

    public void setPetFeederHistoryId(Long petFeederHistoryId) {
        this.petFeederHistoryId = petFeederHistoryId;
    }

    public Long getPetFeeder() {
        return petFeederId;
    }

    public void setPetFeeder(Long petFeederId) {
        this.petFeederId = petFeederId;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public LocalTime getTime() {
        return time;
    }

    public void setTime(LocalTime time) {
        this.time = time;
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

    public int getWaterTemperature() {
        return waterTemperature;
    }

    public void setWaterTemperature(int waterTemperature) {
        this.waterTemperature = waterTemperature;
    }
}
