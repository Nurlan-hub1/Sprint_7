package ru.yandex.praktikum.steps;

import io.qameta.allure.Step;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import ru.yandex.praktikum.steps.config.ApiConfig;
import ru.yandex.praktikum.steps.Order;

import static io.restassured.RestAssured.given;

public class OrderSteps {
    private final String CREATE = "/api/v1/orders";
    private final String LIST = "/api/v1/orders";

    @Step("Создать заказ")
    public Response createOrder(Order order) {
        return given()
                .baseUri(ApiConfig.BASE_URI)
                .contentType(ContentType.JSON)
                .body(order)
                .when()
                .post(CREATE);
    }

    @Step("Получить список заказов")
    public Response getOrdersList() {
        return given()
                .baseUri(ApiConfig.BASE_URI)
                .when()
                .get(LIST);
    }
}
