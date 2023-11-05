package com.example.petfeedercloud.models;


import jakarta.persistence.*;

import java.util.List;

@Entity
public class Schedule {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long scheduleId;
    @ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    private UserP user;
    @OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    public List<Time> time;

    @OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    public List<PetFeeder> petfeederId;
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

    public UserP getUserId() {
        return user;
    }

    public void setUserId(UserP userId) {
        this.user = userId;
    }

    public List<Time> getTime() {
        return time;
    }

    public void setTime(List<Time> time) {
        this.time = time;
    }

    public List<PetFeeder> getPetfeederId() {
        return petfeederId;
    }

    public void setPetfeederId(List<PetFeeder> petfeederId) {
        this.petfeederId = petfeederId;
    }
}
