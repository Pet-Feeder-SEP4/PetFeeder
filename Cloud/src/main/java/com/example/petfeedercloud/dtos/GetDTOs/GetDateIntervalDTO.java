package com.example.petfeedercloud.dtos.GetDTOs;

import java.time.LocalDate;

public class GetDateIntervalDTO {
    private Long petFeederId;
    private LocalDate startDate;
    private LocalDate endDate;

    public GetDateIntervalDTO() {
    }
    // Getters and Setters
    public Long getPetFeederId() {
        return petFeederId;
    }
    public void setPetFeederId(Long petFeederId) {
        this.petFeederId = petFeederId;
    }
    public LocalDate getStartDate() {
        return startDate;
    }
    public void setStartDate(LocalDate startDate) {
        this.startDate = startDate;
    }
    public LocalDate getEndDate() {
        return endDate;
    }
    public void setEndDate(LocalDate endDate) {
        this.endDate = endDate;
    }
}
