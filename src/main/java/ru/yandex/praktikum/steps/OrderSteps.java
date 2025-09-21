package ru.yandex.praktikum.steps;

import io.qameta.allure.Step;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import ru.yandex.praktikum.steps.config.ApiConfig;

import static io.restassured.RestAssured.given;

public class OrderSteps {

    @Step("Создать заказ")
    public Response createOrder(Order order) {
        return given()
                .baseUri(ApiConfig.BASE_URI)
                .contentType(ContentType.JSON)
                .body(order)
                .when()
                .post("/api/v1/orders");
    }

    @Step("Получить список заказов")
    public Response getOrders() {
        return given()
                .baseUri(ApiConfig.BASE_URI)
                .when()
                .get("/api/v1/orders");
    }
}