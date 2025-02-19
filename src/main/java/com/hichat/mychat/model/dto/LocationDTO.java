package com.hichat.mychat.model.dto;

import com.hichat.mychat.model.enumclass.OperationEnum;
import jakarta.validation.constraints.NotNull;

public class LocationDTO {

    @NotNull
    private OperationEnum operation;

    @NotNull
    private Double lat;

    @NotNull
    private Double lng;

    public OperationEnum getOperation() {
        return operation;
    }

    public void setOperation(OperationEnum operation) {
        this.operation = operation;
    }

    public Double getLat() {
        return lat;
    }

    public void setLat(Double lat) {
        this.lat = lat;
    }

    public Double getLng() {
        return lng;
    }

    public void setLng(Double lng) {
        this.lng = lng;
    }

    @Override
    public String toString() {
        return "LocationDTO{" +
                "operation=" + operation +
                ", lat=" + lat +
                ", lng=" + lng +
                '}';
    }
}
