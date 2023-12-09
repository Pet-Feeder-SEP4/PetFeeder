package com.example.petfeedercloud.dtos.GetDTOs;

public class GetNotificationDTO {
    private Long notificationId;
    private int foodLevel;
    private int foodHumidity;
    private int waterTemperture;
    private int waterLevel;
    private boolean active;
    private Long petFeederId;

    public Long getNotificationId() {
        return notificationId;
    }

    public void setNotificationId(Long notificationId) {
        this.notificationId = notificationId;
    }

    public Long getPetFeederId() {
        return petFeederId;
    }

    public void setPetFeederId(Long petFeederId) {
        this.petFeederId = petFeederId;
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

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public Long getPetFeeder() {
        return petFeederId;
    }

    public void setPetFeeder(Long petFeederId) {
        this.petFeederId = petFeederId;
    }
}
