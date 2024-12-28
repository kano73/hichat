package com.hichat.mychat.model.entitie;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import java.time.LocalDateTime;

public class Message {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name="message", nullable = false)
    private String message;

    @Column(name="time_stamp", nullable = false)
    private LocalDateTime timeStamp;

    @Column(name="is_read", nullable = false)
    private boolean isRead = false;

    @ManyToOne
    @JoinColumn(name = "sender_id", nullable = false)
    private MyUser sender;

    @PrePersist
    protected void onCreate() {
        this.timeStamp = LocalDateTime.now();
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public LocalDateTime getTimeStamp() {
        return timeStamp;
    }

    public void setTimeStamp(LocalDateTime timeStamp) {
        this.timeStamp = timeStamp;
    }

    public boolean isRead() {
        return isRead;
    }

    public void setRead(boolean read) {
        isRead = read;
    }

    public MyUser getSender() {
        return sender;
    }

    public void setSender(MyUser sender) {
        this.sender = sender;
    }
}
