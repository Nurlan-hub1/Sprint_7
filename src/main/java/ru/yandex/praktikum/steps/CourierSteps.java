package ru.yandex.praktikum.steps;

import io.qameta.allure.Step;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import ru.yandex.praktikum.steps.config.ApiConfig;
import ru.yandex.praktikum.steps.models.Courier;
import ru.yandex.praktikum.steps.models.CourierCredentials;

import static io.restassured.RestAssured.given;

public class CourierSteps {

    @Step("Создать курьера")
    public Response createCourier(Courier courier) {
        return given()
                .baseUri(ApiConfig.BASE_URI)
                .contentType(ContentType.JSON)
                .body(courier)
                .when()
                .post("/api/v1/courier");
    }

    @Step("Авторизация курьера")
    public Response loginCourier(CourierCredentials credentials) {
        return given()
                .baseUri(ApiConfig.BASE_URI)
                .contentType(ContentType.JSON)
                .body(credentials)
                .when()
                .post("/api/v1/courier/login");
    }

    @Step("Удалить курьера по id")
    public Response deleteCourier(int courierId) {
        return given()
                .baseUri(ApiConfig.BASE_URI)
                .when()
                .delete("/api/v1/courier/" + courierId);
    }
}