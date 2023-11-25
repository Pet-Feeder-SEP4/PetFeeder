package com.example.petfeedercloud.models;


import jakarta.persistence.*;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Date;
@Entity
public class DailyAverage {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long dailyAverageId;
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "petFeederHistoryId")
    private PetFeederHistory petFeederHistory;
    private LocalDate date;
    private int foodLevel;
    private int foodHumidity;
    private int waterTemperature;
    public DailyAverage(){

    }

    public Long getDailyAverageId() {
        return dailyAverageId;
    }

    public void setDailyAverageId(Long dailyAverageId) {
        this.dailyAverageId = dailyAverageId;
    }

    public PetFeederHistory getPetFeederHistory() {
        return petFeederHistory;
    }

    public void setPetFeederHistory(PetFeederHistory petFeederHistory) {
        this.petFeederHistory = petFeederHistory;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
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