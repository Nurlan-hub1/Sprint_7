package ru.yandex.praktikum.steps;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import static org.apache.commons.lang3.RandomStringUtils.randomAlphabetic;

public class Order {
    private String firstName;
    private String lastName;
    private String address;
    private String metroStation;
    private String phone;
    private int rentTime;
    private String deliveryDate;
    private String comment;
    private String[] color;

    public Order(String[] color) {
        this.firstName = randomAlphabetic(15);
        this.lastName = randomAlphabetic(20);
        this.address = randomAlphabetic(100);
        this.metroStation = randomAlphabetic(16);
        this.phone = randomAlphabetic(10);
        this.rentTime = (int) (1 + Math.random()) * 365;
        DateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        this.deliveryDate = format.format(new Date()).toString();
        this.comment = randomAlphabetic(150);
        this.color = color;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getAddress() {
        return address;
    }

    public String getMetroStation() {
        return metroStation;
    }

    public String getPhone() {
        return phone;
    }

    public int getRentTime() {
        return rentTime;
    }

    public String getDeliveryDate() {
        return deliveryDate;
    }

    public String getComment() {
        return comment;
    }

    public String[] getColor() {
        return color;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public void setMetroStation(String metroStation) {
        this.metroStation = metroStation;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public void setRentTime(int rentTime) {
        this.rentTime = rentTime;
    }

    public void setDeliveryDate(String deliveryDate) {
        this.deliveryDate = deliveryDate;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public void setColor(String[] color) {
        this.color = color;
    }

    public Order() {

    }
}
