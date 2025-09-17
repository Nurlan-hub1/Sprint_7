package ru.yandex.praktikum;

import io.qameta.allure.Step;
import io.qameta.allure.junit4.DisplayName;
import org.junit.After;
import org.junit.Test;
import ru.yandex.praktikum.steps.CourierSteps;

import static org.apache.commons.lang3.RandomStringUtils.randomAlphabetic;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;

public class CourierLoginTest {
    String loginCourier;
    String passwordCourier;

    private CourierSteps courier = new CourierSteps();

    @Test
    @DisplayName("Успешная авторизация курьером")
    @Step("Создание курьера и авторизация им")
    public void loginCourierTrue() {
        loginCourier = randomAlphabetic(12);
        passwordCourier = randomAlphabetic(10);

        courier
                .createCourier(loginCourier, passwordCourier, "")
                .then()
                .statusCode(201)
                .body("ok", is(true));

        courier
                .loginCourier(loginCourier, passwordCourier)
                .statusCode(200)
                .body("id", notNullValue());
    }

    @Test
    @DisplayName("Попытка авторизации с несуществующим логином")
    @Step("Создаем курьера, но авторизуется с несуществующим логином")
    public void loginCourierFalseLogin() {
        loginCourier = randomAlphabetic(12);
        passwordCourier = randomAlphabetic(10);

        courier
                .createCourier(loginCourier, passwordCourier, "")
                .then()
                .statusCode(201)
                .body("ok", is(true));

        String loginCourierFalse = randomAlphabetic(12);

        courier
                .loginCourier(loginCourierFalse, passwordCourier)
                .statusCode(404)
                .body("message", is("Учетная запись не найдена"));
    }

    @Test
    @DisplayName("Попытка авторизации с неверным паролем")
    @Step("Создаем курьера, но авторизуется с неверным паролем")
    public void loginCourierFalsePassword() {
        loginCourier = randomAlphabetic(12);
        passwordCourier = randomAlphabetic(10);

        courier
                .createCourier(loginCourier, passwordCourier, "")
                .then()
                .statusCode(201)
                .body("ok", is(true));

        String passwordCourierFalse = randomAlphabetic(10);

        courier
                .loginCourier(loginCourier, passwordCourierFalse)
                .statusCode(404)
                .body("message", is("Учетная запись не найдена"));
    }

    @Test
    @DisplayName("Попытка авторизации без логина")
    @Step("Создаем курьера, но авторизуемся без указания логина")
    public void loginCourierFalseWithoutLogin() {
        loginCourier = randomAlphabetic(12);
        passwordCourier = randomAlphabetic(10);

        courier
                .createCourier(loginCourier, passwordCourier, "")
                .then()
                .statusCode(201)
                .body("ok", is(true));

        courier
                .loginCourier("", passwordCourier)
                .statusCode(400)
                .body("message", is("Недостаточно данных для входа"));
    }

    @Test
    @DisplayName("Попытка авторизации без пароля")
    @Step("Создаем курьера, но авторизуемся без указания пароля")
    public void loginCourierFalseWithoutPassword() {
        loginCourier = randomAlphabetic(12);
        passwordCourier = randomAlphabetic(10);

        courier
                .createCourier(loginCourier, passwordCourier, "")
                .then()
                .statusCode(201)
                .body("ok", is(true));

        courier
                .loginCourier(loginCourier, "")
                .statusCode(400)
                .body("message", is("Недостаточно данных для входа"));
    }

    @After
    public void dataCleaning() {
        Integer idCourier = courier.loginCourier(loginCourier, passwordCourier)
                .extract()
                .body()
                .path("id");
        if (idCourier != null) {
            courier.deleteCourier(idCourier.toString());
        }
    }
}
