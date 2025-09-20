package ru.yandex.praktikum.steps.models;

public class CourierCredentials {
    private String login;
    private String password;

    public CourierCredentials() {}

    public CourierCredentials(String login, String password) {
        this.login = login;
        this.password = password;
    }

    public String getLogin() {
        return login;
    }

    public String getPassword() {
        return password;
    }
}