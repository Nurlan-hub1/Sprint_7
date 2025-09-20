package ru.yandex.praktikum;

import io.qameta.allure.Step;
import org.junit.After;
import org.junit.BeforeClass;
import org.junit.Test;
import ru.yandex.praktikum.steps.CourierSteps;
import ru.yandex.praktikum.steps.models.Courier;
import io.restassured.RestAssured;
import io.restassured.parsing.Parser;

import static org.apache.commons.lang3.RandomStringUtils.randomAlphabetic;
import static org.apache.http.HttpStatus.*;
import static org.hamcrest.Matchers.containsString;
import static org.hamcrest.Matchers.is;

public class CourierTest {

    private String loginCourier;
    private String passwordCourier;
    private String firstNameCourier;
    private final CourierSteps courier = new CourierSteps();
    private Integer courierId;

    @BeforeClass
    public static void setUpRestAssured() {
        // Позволяет работать с JSON даже если сервер не вернул Content-Type
        RestAssured.defaultParser = Parser.JSON;
    }

    @Test
    @Step("Создание курьера с login, password, firstName")
    public void createCourierTrue() {
        initCourierData();
        Courier newCourier = new Courier(loginCourier, passwordCourier, firstNameCourier);

        courierId = courier.createCourier(newCourier)
                .then()
                .statusCode(SC_CREATED)
                .body("ok", is(true))
                .extract()
                .path("id");
    }

    @Test
    @Step("Создание курьера без firstName")
    public void createCourierTrueWithoutFirstName() {
        loginCourier = randomAlphabetic(12);
        passwordCourier = randomAlphabetic(10);

        Courier newCourier = new Courier(loginCourier, passwordCourier, "");

        courierId = courier.createCourier(newCourier)
                .then()
                .statusCode(SC_CREATED)
                .body("ok", is(true))
                .extract()
                .path("id");
    }

    @Test
    @Step("Попытка создания курьера без login")
    public void createCourierFalseWithoutLogin() {
        passwordCourier = randomAlphabetic(10);
        firstNameCourier = randomAlphabetic(8);

        Courier newCourier = new Courier("", passwordCourier, firstNameCourier);

        courier.createCourier(newCourier)
                .then()
                .statusCode(SC_BAD_REQUEST)
                .body("message", containsString("Недостаточно данных для создания учетной записи"));
    }

    @Test
    @Step("Попытка создания курьера без password")
    public void createCourierFalseWithoutPassword() {
        loginCourier = randomAlphabetic(12);
        firstNameCourier = randomAlphabetic(8);

        Courier newCourier = new Courier(loginCourier, "", firstNameCourier);

        courier.createCourier(newCourier)
                .then()
                .statusCode(SC_BAD_REQUEST)
                .body("message", containsString("Недостаточно данных для создания учетной записи"));
    }

    @Test
    @Step("Попытка создания дубликата курьера")
    public void createDoubleCourierFalse() {
        initCourierData();
        Courier newCourier = new Courier(loginCourier, passwordCourier, firstNameCourier);

        // Создаем первый раз
        courierId = courier.createCourier(newCourier)
                .then()
                .statusCode(SC_CREATED)
                .body("ok", is(true))
                .extract()
                .path("id");

        // Попытка создать дубликат
        courier.createCourier(newCourier)
                .then()
                .statusCode(SC_CONFLICT)
                .body("message", containsString("Этот логин уже используется"));
    }

    @After
    public void dataCleaning() {
        if (loginCourier == null || loginCourier.isEmpty() ||
                passwordCourier == null || passwordCourier.isEmpty() ||
                courierId == null) {
            return;
        }

        try {
            courier.deleteCourier(courierId);
        } catch (Exception e) {
            System.out.println("Курьер не был создан или уже удален: " + e.getMessage());
        }
    }

    // Вспомогательный метод для инициализации данных курьера
    private void initCourierData() {
        loginCourier = randomAlphabetic(12);
        passwordCourier = randomAlphabetic(10);
        firstNameCourier = randomAlphabetic(8);
    }
}