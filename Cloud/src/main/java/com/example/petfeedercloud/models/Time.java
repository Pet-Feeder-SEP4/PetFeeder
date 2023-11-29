package com.example.petfeedercloud.models;

import jakarta.persistence.*;

import java.time.LocalTime;

@Entity
public class Time {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long timeId;
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "scheduleId") // Name of the foreign key column in the schedule table
    private Schedule schedule;
    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "portionId") // Name of the foreign key column in the Portion table
    private Portion portion;
    private String timeLabel;
    private LocalTime time;
    public Time(){

    }
    public Portion getPortion() {
        return portion;
    }

    public void setPortion(Portion portion) {
        this.portion = portion;
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

    public LocalTime getTime() {
        return time;
    }

    public void setTime(LocalTime time) {
        this.time = time;
    }

}
