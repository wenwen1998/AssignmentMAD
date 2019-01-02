package com.example.acer.lltgh_dogadoption;

import java.util.Date;

public class DogEntity {
    private String name;
    private String breed;
    private String sex;
    private String size;
    private String age;
    private String desc;
    private String image;
    private String ownerName;
    private String contactNum;
    private String reason;
    private boolean adopted;
    private Date dateTime;

    public DogEntity() {
    }

    public DogEntity(String name, String breed, String sex, String size, String age, String desc, String image, String ownerName, String contactNum, String reason, boolean adapted, Date dateTime) {
        this.name = name;
        this.breed = breed;
        this.sex = sex;
        this.size = size;
        this.age = age;
        this.desc = desc;
        this.image = image;
        this.ownerName = ownerName;
        this.contactNum = contactNum;
        this.reason = reason;
        this.adopted = adopted;
        this.dateTime = dateTime;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getBreed() {
        return breed;
    }

    public void setBreed(String breed) {
        this.breed = breed;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public String getSize() {
        return size;
    }

    public void setSize(String size) {
        this.size = size;
    }

    public String getAge() {
        return age;
    }

    public void setAge(String age) {
        this.age = age;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getOwnerName() {
        return ownerName;
    }

    public void setOwnerName(String ownerName) {
        this.ownerName = ownerName;
    }

    public String getContactNum() {
        return contactNum;
    }

    public void setContactNum(String contactNum) {
        this.contactNum = contactNum;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    public boolean isAdapted() {
        return adopted;
    }

    public void setAdapted(boolean adapted) {
        this.adopted = adapted;
    }

    public Date getDateTime() {
        return dateTime;
    }

    public void setDateTime(Date dateTime) {
        this.dateTime = dateTime;
    }
}
