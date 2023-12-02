package com.example.petfeedercloud.dtos.GetDTOs;

import com.example.petfeedercloud.models.Schedule;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;

public class GetTimeDTO {

    private Long timeId;
    private Long scheduleId;
    private String timeLabel;
    private String time;

    public GetTimeDTO(Long timeId, Long scheduleId, String timeLabel, String time) {
        this.timeId = timeId;
        this.scheduleId = scheduleId;
        this.timeLabel = timeLabel;
        this.time = time;
    }

    public Long getTimeId() {
        return timeId;
    }

    public void setTimeId(Long timeId) {
        this.timeId = timeId;
    }

    public Long getScheduleId() {
        return scheduleId;
    }

    public void setScheduleId(Long scheduleId) {
        this.scheduleId = scheduleId;
    }

    public String getTimeLabel() {
        return timeLabel;
    }

    public void setTimeLabel(String timeLabel) {
        this.timeLabel = timeLabel;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }
}
