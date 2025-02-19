package com.hichat.mychat.model.entitie;

import jakarta.persistence.*;

@Entity
@Table(name="users_location")
public class UsersLocation {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name="lat", nullable = false)
    private Double lat;

    @Column(name="lng", nullable = false)
    private Double lng;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private MyUser user;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
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

    public MyUser getUser() {
        return user;
    }

    public void setUser(MyUser user) {
        this.user = user;
    }
}
