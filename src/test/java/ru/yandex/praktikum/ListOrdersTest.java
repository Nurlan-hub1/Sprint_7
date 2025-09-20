package ru.yandex.praktikum;

import io.qameta.allure.Step;
import io.restassured.http.ContentType;
import org.junit.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.apache.http.HttpStatus.SC_OK;

public class ListOrdersTest {
    public final String BASE_URI = "https://qa-scooter.praktikum-services.ru";
    public final String GET_ORDERS = "/api/v1/orders";

    @Test
    @Step("Получение списка заказов")
    public void getListOrders() {
        given()
                .contentType(ContentType.JSON)
                .baseUri(BASE_URI)
                .get(GET_ORDERS)
                .then()
                .statusCode(SC_OK)
                .body("orders", notNullValue());
    }
}