package com.example.petfeedercloud.models;


import jakarta.persistence.*;

import java.util.List;

@Entity
public class Schedule {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long scheduleId;

    private Long userId;
    @OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    public List<Time> time;

    @OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    public List<PetFeeder> petFeeder;

    private String scheduleLabel;

    public Schedule() {
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

    public List<Time> getTime() {
        return time;
    }

    public void setTime(List<Time> time) {
        this.time = time;
    }

    public List<PetFeeder> getPetFeeder() {
        return petFeeder;
    }

    public void setPetFeeder(List<PetFeeder> petFeeder) {
        this.petFeeder = petFeeder;
    }
}
