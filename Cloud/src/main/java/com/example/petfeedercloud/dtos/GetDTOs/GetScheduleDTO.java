package com.example.petfeedercloud.dtos.GetDTOs;

public class GetScheduleDTO {
    private Long scheduleId;
    private String scheduleLabel;

    private Long petFeederId;
    private Boolean active;
    public GetScheduleDTO(Long scheduleId, String scheduleLabel, Long petFeederId,Boolean active) {
        this.scheduleId = scheduleId;
        this.scheduleLabel = scheduleLabel;

        this.petFeederId=petFeederId;
        this.active=active;
    }
    public Boolean getActive() {
        return active;
    }
    public void setActive(Boolean active) {
        this.active = active;
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


    public Long getPetFeederId() {
        return petFeederId;
    }

    public void setPetFeederId(Long petFeederId) {
        this.petFeederId = petFeederId;
    }
}
