package com.example.petfeedercloud.models;

import jakarta.persistence.*;

import java.util.List;

@Entity
public class PetFeeder {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long petFeederId;
    @ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    private Schedule schedule;
    @ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    private UserP user;
    @OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    public List<Pet> pets;

    private String petFeederLabel;
    private String foodLevel;
    private String lowLevelFood;
    private String foodHumidity;
    private String waterTemperture;

    public PetFeeder() {

    }

    public Schedule getSchedule() {
        return schedule;
    }

    public void setSchedule(Schedule schedule) {
        this.schedule = schedule;
    }

    public UserP getUser() {
        return user;
    }

    public void setUser(UserP user) {
        this.user = user;
    }

    public List<Pet> getPets() {
        return pets;
    }

    public void setPets(List<Pet> pets) {
        this.pets = pets;
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
