package com.example.petfeedercloud.dtos;

import com.example.petfeedercloud.models.UserP;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;

public class ScheduleDTO {
    private String scheduleLabel;
    private Long userId;

    private Long petFeederId;

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Long getPetFeederId() {
        return petFeederId;
    }

    public void setPetFeederId(Long petFeederId) {
        this.petFeederId = petFeederId;
    }

    public String getScheduleLabel() {
        return scheduleLabel;
    }

    public void setScheduleLabel(String scheduleLabel) {
        this.scheduleLabel = scheduleLabel;
    }
}
