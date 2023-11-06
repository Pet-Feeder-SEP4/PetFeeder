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
    private String foodLevel;
    private String lowLevelFood;
    private String foodHumidity;
    private String waterTemperture;
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "userId") // Name of the foreign key column in the pet_feeder table
    private UserP user;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "petId") // Name of the foreign key column in the pet_feeder table
    private Pet pet;
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "scheduleId") // Name of the foreign key column in the pet_feeder table
    private Schedule schedule;

    public PetFeeder() {

    }
    public UserP getUser() {
        return user;
    }
    public void setUser(UserP user) {
        this.user = user;
    }
    public Schedule getSchedule() {
        return schedule;
    }
    public void setSchedule(Schedule schedule) {
        this.schedule = schedule;
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
