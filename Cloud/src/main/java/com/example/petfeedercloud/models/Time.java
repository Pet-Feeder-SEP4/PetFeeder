package com.example.petfeedercloud.models;

import jakarta.persistence.*;

import java.time.LocalTime;

@Entity
public class Time {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long timeId;
    @ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    private Schedule scheduleId;
    private String timeLabel;
    private LocalTime time;
    public Time(){

    }

    public Long getTimeId() {
        return timeId;
    }

    public void setTimeId(Long timeId) {
        this.timeId = timeId;
    }

    public String getTimeLabel() {
        return timeLabel;
    }

    public void setTimeLabel(String timeLabel) {
        this.timeLabel = timeLabel;
    }

    public LocalTime getTime() {
        return time;
    }

    public void setTime(LocalTime time) {
        this.time = time;
    }

    public Schedule getScheduleId() {
        return scheduleId;
    }

    public void setScheduleId(Schedule scheduleId) {
        this.scheduleId = scheduleId;
    }
}
