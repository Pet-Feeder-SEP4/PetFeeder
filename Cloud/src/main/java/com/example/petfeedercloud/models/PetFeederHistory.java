package com.example.petfeedercloud.models;

import jakarta.persistence.*;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Date;
@Entity
public class PetFeederHistory {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long petFeederHistoryId;
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "petFeederId", referencedColumnName = "petFeederId")
    private PetFeeder petFeeder;
    private LocalDate date;
    private LocalTime time;
    private int foodLevel;
    private int foodHumidity;
    private int waterTemperature;
    public PetFeederHistory(){

    }

    public Long getPetFeederHistoryId() {
        return petFeederHistoryId;
    }

    public void setPetFeederHistoryId(Long petFeederHistoryId) {
        this.petFeederHistoryId = petFeederHistoryId;
    }

    public PetFeeder getPetFeeder() {
        return petFeeder;
    }

    public void setPetFeeder(PetFeeder petFeeder) {
        this.petFeeder = petFeeder;
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