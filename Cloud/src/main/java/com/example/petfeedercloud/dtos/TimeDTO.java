package com.example.petfeedercloud.dtos;

import java.time.LocalTime;

public class TimeDTO {
    private Long scheduleId;

    private String  time;
    private int portionSize;
    private String timeLabel;

    public Long getScheduleId() {
        return scheduleId;
    }

    public void setScheduleId(Long scheduleId) {
        this.scheduleId = scheduleId;
    }



    public String  getTime() {
        return time;
    }

    public void setTime(String  time) {
        this.time = time;
    }

    public String getTimeLabel() {
        return timeLabel;
    }

    public void setTimeLabel(String timeLabel) {
        this.timeLabel = timeLabel;
    }

    public int getPortionSize() {
        return portionSize;
    }

    public void setPortionSize(int portionSize) {
        this.portionSize = portionSize;
    }
}
