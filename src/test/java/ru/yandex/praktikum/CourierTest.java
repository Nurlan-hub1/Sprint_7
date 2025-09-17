package ru.yandex.praktikum;

import io.qameta.allure.Step;
import io.qameta.allure.junit4.DisplayName;
import org.junit.After;
import org.junit.Test;
import ru.yandex.praktikum.steps.CourierSteps;

import static org.apache.commons.lang3.RandomStringUtils.randomAlphabetic;
import static org.hamcrest.Matchers.is;

public class CourierTest {
    String loginCourier;
    String passwordCourier;
    String firstNameCourier;

    private CourierSteps courier = new CourierSteps();

    @Test
    @DisplayName("Создание курьера с заполнением всех полей")
    @Step("Создание курьера")
    public void createCourierTrue() {
        loginCourier = randomAlphabetic(12);
        passwordCourier = randomAlphabetic(10);
        firstNameCourier = randomAlphabetic(8);

        courier
                .createCourier(loginCourier, passwordCourier, firstNameCourier)
                .then()
                .statusCode(201)
                .body("ok", is(true));
    }

    @Test
    @DisplayName("Создание курьера только с заполнением обязательных полей")
    @Step("Создание курьера")
    public void createCourierTrueWithoutFirstName() {
        loginCourier = randomAlphabetic(12);
        passwordCourier = randomAlphabetic(10);

        courier
                .createCourier(loginCourier, passwordCourier, "")
                .then()
                .statusCode(201)
                .body("ok", is(true));
    }

    @Test
    @DisplayName("Невозможность создания курьера без логина")
    @Step("Попытка создания курьера")
    public void createCourierFalseWithoutLogin() {
        passwordCourier = randomAlphabetic(10);
        firstNameCourier = randomAlphabetic(8);

        courier
                .createCourier("", passwordCourier, firstNameCourier)
                .then()
                .statusCode(400)
                .body("message", is("Недостаточно данных для создания учетной записи"));
    }

    @Test
    @DisplayName("Невозможность создания курьера без пароля")
    @Step("Попытка создания курьера")
    public void createCourierFalseWithoutPassword() {
        loginCourier = randomAlphabetic(12);
        firstNameCourier = randomAlphabetic(8);

        courier
                .createCourier(loginCourier, "", firstNameCourier)
                .then()
                .statusCode(400)
                .body("message", is("Недостаточно данных для создания учетной записи"));
    }

    @Test
    @DisplayName("Невозможность создания двух курьеров с одинаковым логином")
    @Step("Попытка создания курьера")
    public void createDoubleCourierFalse() {
        loginCourier = randomAlphabetic(12);
        passwordCourier = randomAlphabetic(10);
        firstNameCourier = randomAlphabetic(8);

        // Создадим курьера первый раз
        courier
                .createCourier(loginCourier, passwordCourier, firstNameCourier)
                .then()
                .statusCode(201)
                .body("ok", is(true));

        // Попробуем создать дубликат
        courier
                .createCourier(loginCourier, passwordCourier, firstNameCourier)
                .then()
                .statusCode(409)
                .body("message", is("Этот логин уже используется"));
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
