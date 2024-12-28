package com.hichat.mychat.model.entitie;

import com.hichat.mychat.model.enumclass.ResponseStatusEnum;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Table(name="friends_request")
@AllArgsConstructor
public class FriendsRequest {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "inviter_id", nullable = false)
    private MyUser inviter;

    @ManyToOne
    @JoinColumn(name = "receiver_id", nullable = false)
    private MyUser receiver;

    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false)
    private ResponseStatusEnum status = ResponseStatusEnum.WAITING;

    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @PrePersist
    protected void onCreate() {
        this.createdAt = LocalDateTime.now();
    }

    public FriendsRequest() {
    }

    public FriendsRequest(MyUser inviter, MyUser receiver) {
        this.inviter = inviter;
        this.receiver = receiver;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public MyUser getInviter() {
        return inviter;
    }

    public void setInviter(MyUser inviter) {
        this.inviter = inviter;
    }

    public MyUser getReceiver() {
        return receiver;
    }

    public void setReceiver(MyUser receiver) {
        this.receiver = receiver;
    }

    public ResponseStatusEnum getStatus() {
        return status;
    }

    public void setStatus(ResponseStatusEnum status) {
        this.status = status;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }
}
