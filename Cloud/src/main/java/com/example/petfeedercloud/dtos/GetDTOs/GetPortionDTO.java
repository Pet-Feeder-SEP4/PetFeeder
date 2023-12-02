package com.example.petfeedercloud.dtos.GetDTOs;

import com.example.petfeedercloud.models.Time;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;

public class GetPortionDTO {
    private Long portionId;
    private String Label;
    private int portionSize;
    private Long time;

    public GetPortionDTO(Long portionId, String label, int portionSize, Long time) {
        this.portionId = portionId;
        Label = label;
        this.portionSize = portionSize;
        this.time = time;
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

    public Long getTime() {
        return time;
    }

    public void setTime(Long time) {
        this.time = time;
    }
}
