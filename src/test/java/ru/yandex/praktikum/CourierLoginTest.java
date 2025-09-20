package ru.yandex.praktikum;

import io.qameta.allure.junit4.DisplayName;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import ru.yandex.praktikum.steps.CourierSteps;
import ru.yandex.praktikum.steps.models.Courier;
import ru.yandex.praktikum.steps.models.CourierCredentials;

import static org.apache.commons.lang3.RandomStringUtils.randomAlphabetic;
import static org.apache.http.HttpStatus.*;
import static org.hamcrest.Matchers.notNullValue;

public class CourierLoginTest {
    private String login;
    private String password;
    private CourierSteps courier;
    private Integer courierId;

    @Before
    public void setUp() {
        courier = new CourierSteps();
        login = randomAlphabetic(12);
        password = randomAlphabetic(10);

        // Создаем уникального курьера
        Courier newCourier = new Courier(login, password, randomAlphabetic(8));
        courierId = courier.createCourier(newCourier)
                .then()
                .statusCode(SC_CREATED)
                .extract()
                .path("id");
    }

    @Test
    @DisplayName("Курьер может войти в систему")
    public void courierCanLogin() {
        CourierCredentials creds = new CourierCredentials(login, password);

        courier.loginCourier(creds)
                .then()
                .statusCode(SC_OK)
                .body("id", notNullValue());
    }

    @After
    public void tearDown() {
        if (courierId != null) {
            courier.deleteCourier(courierId);
        }
    }
}