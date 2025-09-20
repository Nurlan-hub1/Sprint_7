package ru.yandex.praktikum;

import io.qameta.allure.Step;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import ru.yandex.praktikum.steps.OrderSteps;
import ru.yandex.praktikum.steps.Order;

import static org.hamcrest.Matchers.notNullValue;
import static org.apache.http.HttpStatus.SC_CREATED;

@RunWith(Parameterized.class)
public class OrderCreateTest {
    private final OrderSteps orderSteps = new OrderSteps();
    private final String[] color;

    @Parameterized.Parameters
    public static Object[][] color() {
        return new Object[][]{
                {new String[]{"BLACK"}},
                {new String[]{"GREY"}},
                {new String[]{"BLACK", "GREY"}},
                {new String[]{}},
        };
    }

    public OrderCreateTest(String[] color) {
        this.color = color;
    }

    @Test
    @Step("Создание заказа")
    public void createOrderTest() {
        Order order = new Order(color);

        orderSteps
                .createOrder(order)
                .then()
                .statusCode(SC_CREATED)
                .body("track", notNullValue());
    }
}