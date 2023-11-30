package com.example.petfeedercloud.dtos.GetDTOs;

import java.time.LocalDate;

public class GetHistoryDTO {
    private Long petFeederId;
    private LocalDate date;

    public GetHistoryDTO() {
    }
    public Long getPetFeederId() {
        return petFeederId;
    }

    public void setPetFeederId(Long petFeederId) {
        this.petFeederId = petFeederId;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }
}