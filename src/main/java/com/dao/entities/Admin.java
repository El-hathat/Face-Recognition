package com.dao.entities;

public class Admin {

    private long id;
    private String username;

    public Admin() {}

    public Admin(long id , String username) {
        this.username = username;
    }


    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }


    public void setUsername(String username) {
        this.username = username;
    }

}
