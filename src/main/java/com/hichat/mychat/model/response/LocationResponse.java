package com.hichat.mychat.model.response;

import com.hichat.mychat.model.dto.LocationDTO;
import com.hichat.mychat.model.entitie.MyUser;
import com.hichat.mychat.model.enumclass.OperationEnum;
import jakarta.validation.constraints.NotNull;

public class LocationResponse {

    @NotNull
    private OperationEnum operation;

    @NotNull
    private Double lat;

    @NotNull
    private Double lng;

    @NotNull
    public MyUserPublic user;

    public void setLatAndLng(LocationDTO locationDTO){
        this.lat = locationDTO.getLat();
        this.lng = locationDTO.getLng();
    }

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

    public MyUserPublic getUser() {
        return user;
    }

    public void setUser(MyUserPublic user) {
        this.user = user;
    }

    public static LocationResponse fromLocationDTO(MyUser user, LocationDTO locationDTO){
        LocationResponse locationResponse = new LocationResponse();
        locationResponse.setOperation(locationDTO.getOperation());
        locationResponse.setUser(MyUserPublic.fromUserToPublic(user));
        locationResponse.setLng(locationDTO.getLng());
        locationResponse.setLat(locationDTO.getLat());

        return locationResponse;
    }

    @Override
    public String toString() {
        return "LocationResponse{" +
                "operation=" + operation +
                ", lat=" + lat +
                ", lng=" + lng +
                ", user=" + user +
                '}';
    }
}
