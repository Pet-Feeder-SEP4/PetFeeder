package com.example.petfeedercloud.models;


import jakarta.persistence.*;

import java.util.List;
import java.util.Optional;

@Entity
public class Schedule {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long scheduleId;
    private String scheduleLabel;
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "userId") // Name of the foreign key column in the pet_feeder table
    private UserP user;
    private Boolean active;
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "petFeederId") // Name of the foreign key column in the pet_feeder table
    private PetFeeder petFeeder;

    public Schedule() {
    }
    public Boolean getActive() {
        return active;
    }
    public void setActive(Boolean active) {
        this.active = active;
    }
    public PetFeeder getPetFeeder() {
        return petFeeder;
    }
    public void setPetFeeder(PetFeeder petFeeder) {
        this.petFeeder = petFeeder;
    }
    public UserP getUser() {
        return user;
    }
    public void setUser(UserP user) {
        this.user = user;
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


}
