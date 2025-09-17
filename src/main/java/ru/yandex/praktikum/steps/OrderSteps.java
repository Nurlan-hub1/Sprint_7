package ru.yandex.praktikum.steps;

import io.restassured.response.Response;
import org.apache.http.entity.ContentType;

import static io.restassured.RestAssured.given;

public class OrderSteps {
    public final String BASE_URI = "https://qa-scooter.praktikum-services.ru";
    public final String POST_CREATE = "/api/v1/orders";

    public Response createOrder(String[] color) {
        Order order = new Order(color);
        return given()
                .contentType(String.valueOf(ContentType.APPLICATION_JSON))
                .baseUri(BASE_URI)
                .body(order)
                .when()
                .post(POST_CREATE);
    }
}
