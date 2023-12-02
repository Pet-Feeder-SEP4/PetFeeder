package com.example.petfeedercloud.dtos.GetDTOs;

public class GetScheduleDTO {
    private Long scheduleId;
    private String scheduleLabel;
    private Long userId;
    private Long petFeederId;

    public GetScheduleDTO(Long scheduleId, String scheduleLabel, Long userId,Long petFeederId) {
        this.scheduleId = scheduleId;
        this.scheduleLabel = scheduleLabel;
        this.userId = userId;
        this.petFeederId=petFeederId;
    }

    public Long getScheduleId() {
        return scheduleId;
    }

    public void setScheduleId(Long scheduleId) {
        this.scheduleId = scheduleId;
    }

    public String getScheduleLabel() {
        return scheduleLabel;
    }

    public void setScheduleLabel(String scheduleLabel) {
        this.scheduleLabel = scheduleLabel;
    }

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
}
