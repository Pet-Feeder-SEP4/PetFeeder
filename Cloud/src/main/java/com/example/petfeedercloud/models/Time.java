package com.example.petfeedercloud.models;

import jakarta.annotation.Nullable;
import jakarta.persistence.*;

import java.time.LocalTime;

@Entity
public class Time {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long timeId;
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "scheduleId")
    private Schedule schedule;
    @Nullable
    private int portionSize;
    private String timeLabel;
    private String time;
    public Time(){

    }

    public Schedule getSchedule() {
        return schedule;
    }

    public void setSchedule(Schedule schedule) {
        this.schedule = schedule;
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

    public String  getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public int getPortionSize() {
        return portionSize;
    }

    public void setPortionSize(int portionSize) {
        this.portionSize = portionSize;
    }
}
