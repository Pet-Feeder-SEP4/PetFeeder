package com.example.petfeedercloud.models;

import jakarta.persistence.*;

import java.time.LocalTime;

@Entity
public class Portion {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long portionId;
    private String Label;
    private int portionSize;
    public Portion(){

    }

    public Long getPortionId() {
        return portionId;
    }

    public void setPortionId(Long portionId) {
        this.portionId = portionId;
    }

    public String getLabel() {
        return Label;
    }

    public void setLabel(String label) {
        Label = label;
    }

    public int getPortionSize() {
        return portionSize;
    }

    public void setPortionSize(int portionSize) {
        this.portionSize = portionSize;
    }
}