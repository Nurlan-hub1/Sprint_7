package ru.yandex.praktikum;

import org.junit.Test;
import ru.yandex.praktikum.steps.OrderSteps;

import static org.apache.http.HttpStatus.SC_OK;
import static org.hamcrest.Matchers.notNullValue;

public class ListOrdersTest extends BaseTest {

    private final OrderSteps orderSteps = new OrderSteps();

    @Test
    public void getListOrders() {
        orderSteps.getOrders()
                .then()
                .statusCode(SC_OK)
                .body("orders", notNullValue());
    }
}