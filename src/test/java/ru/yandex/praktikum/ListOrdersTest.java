package ru.yandex.praktikum;

import io.qameta.allure.Step;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.http.ContentType;
import org.junit.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.notNullValue;

public class ListOrdersTest {
    public final String BASE_URI = "https://qa-scooter.praktikum-services.ru";
    public final String GET_ORDERS = "/api/v1/orders";

    @Test
    @DisplayName("Запрос на получение списка заказов")
    @Step("Получение списка заказов")
    public void getListOrders() {
        given()
                .contentType(ContentType.JSON)
                .baseUri(BASE_URI)
                .get(GET_ORDERS)
                .then()
                .statusCode(200)
                .body("orders", notNullValue());
    }
}
