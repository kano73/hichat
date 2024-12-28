package com.hichat.mychat.model.entitie;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;

@Entity
@Table(name = "friends", uniqueConstraints = {
        @UniqueConstraint(columnNames = {"user_id", "friend_id"}),
        @UniqueConstraint(columnNames = {"friend_id", "user_id"})
})
@AllArgsConstructor
public class Friends {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private MyUser user;

    @ManyToOne
    @JoinColumn(name = "friend_id", nullable = false)
    private MyUser friend;

    public Friends() {
    }

    public Friends(MyUser user, MyUser friend) {
        this.user = user;
        this.friend = friend;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public MyUser getUser() {
        return user;
    }

    public void setUser(MyUser user) {
        this.user = user;
    }

    public MyUser getFriend() {
        return friend;
    }

    public void setFriend(MyUser friend) {
        this.friend = friend;
    }
}
