package com.dao.entities;

public class User {
    private Long id;
    private String name;

    public User(Long id, String name, String email, String tel, String passCode, String imagePath) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.tel = tel;
        this.passCode = passCode;
        this.imagePath = imagePath;
    }

    private  String email;
    private String tel;
    private String passCode;
    private String imagePath;
    public User(){}

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getTel() {
        return tel;
    }

    public void setTel(String tel) {
        this.tel = tel;
    }

    public String getPassCode() {
        return passCode;
    }

    public void setPassCode(String passCode) {
        this.passCode = passCode;
    }

    public String getImagePath() {
        return imagePath;
    }

    public void setImagePath(String imagePath) {
        this.imagePath = imagePath;
    }
}
