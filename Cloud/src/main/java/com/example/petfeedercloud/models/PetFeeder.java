package com.example.petfeedercloud.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;

import java.util.List;

@Entity
public class PetFeeder {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long petFeederId;

    private String petFeederLabel;
    private int foodLevel;
    private int lowLevelFood;
    private int foodHumidity;
    private int waterTemperture;
    private boolean active = false;
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "userId") // Name of the foreign key column in the pet_feeder table
    private UserP user;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "petId") // Name of the foreign key column in the pet_feeder table
    private Pet pet;


    public PetFeeder() {

    }
    public UserP getUser() {
        return user;
    }
    public void setUser(UserP user) {
        this.user = user;
    }

    public String getPetFeederLabel() {
        return petFeederLabel;
    }
    public void setPetFeederLabel(String petFeederLabel) {
        this.petFeederLabel = petFeederLabel;
    }
    public Pet getPet() {
        return pet;
    }
    public void setPet(Pet pet) {
        this.pet = pet;
    }
    public int getFoodLevel() {
        return foodLevel;
    }
    public void setFoodLevel(int foodLevel) {
        this.foodLevel = foodLevel;
    }
    public int getLowLevelFood() {
        return lowLevelFood;
    }
    public void setLowLevelFood(int lowLevelFood) {
        this.lowLevelFood = lowLevelFood;
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
    public void setPetFeederId(Long petFeederId) {
        this.petFeederId = petFeederId;
    }
    public Long getPetFeederId() {
        return petFeederId;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }
}