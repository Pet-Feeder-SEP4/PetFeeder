package com.example.petfeedercloud.models;


import jakarta.persistence.*;

@Entity
public class Notification {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long notificationId;
    private int foodLevel;
    private int foodHumidity;
    private int waterTemperture;
    private int waterLevel;
    private boolean active;
    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "petFeederId")
    private PetFeeder petFeeder;

    public Notification( int foodLevel, int foodHumidity, int waterTemperture, int waterLevel, PetFeeder petFeeder,boolean active) {
        this.foodLevel = foodLevel;
        this.foodHumidity = foodHumidity;
        this.waterTemperture = waterTemperture;
        this.waterLevel = waterLevel;
        this.petFeeder = petFeeder;
        this.active=active;
    }
    public Notification() {
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public Long getNotificationId() {
        return notificationId;
    }

    public void setNotificationId(Long notificationId) {
        this.notificationId = notificationId;
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

    public int getWaterLevel() {
        return waterLevel;
    }

    public void setWaterLevel(int waterLevel) {
        this.waterLevel = waterLevel;
    }

    public PetFeeder getPetFeeder() {
        return petFeeder;
    }

    public void setPetFeeder(PetFeeder petFeeder) {
        this.petFeeder = petFeeder;
    }
}
