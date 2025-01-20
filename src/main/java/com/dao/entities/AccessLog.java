package com.dao.entities;

import java.sql.Date;
import java.sql.Timestamp;

public class AccessLog {

    private Long id;
    private User user;
    private Timestamp accessTime;

    public AccessLog() {
    }

    public AccessLog(Long id , User user, Timestamp accessTime) {
        this.id = id;
        this.user = user;
        this.accessTime = accessTime;

    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Timestamp getAccessTime() {
        return accessTime;
    }

    public void setAccessTime(Timestamp accessTime) {
        this.accessTime = accessTime;
    }

}
