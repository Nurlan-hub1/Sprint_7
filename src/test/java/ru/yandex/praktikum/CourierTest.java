package ru.yandex.praktikum;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import ru.yandex.praktikum.steps.CourierSteps;
import ru.yandex.praktikum.steps.models.Courier;
import ru.yandex.praktikum.steps.models.CourierCredentials;

import static org.apache.http.HttpStatus.*;
import static org.hamcrest.Matchers.containsString;
import static org.hamcrest.Matchers.is;

import static org.apache.commons.lang3.RandomStringUtils.randomAlphabetic;

public class CourierTest extends BaseTest {

    private CourierSteps courierSteps;
    private String loginCourier;
    private String passwordCourier;
    private String firstNameCourier;
    private Integer courierId;

    @Before
    public void setUp() {
        courierSteps = new CourierSteps();
    }

    @After
    public void tearDown() {
        if (loginCourier != null && !loginCourier.isEmpty() &&
                passwordCourier != null && !passwordCourier.isEmpty() &&
                courierId != null) {

            courierSteps.deleteCourier(courierId).then().statusCode(SC_OK);
        }
    }

    @Test
    public void createCourierTrue() {
        loginCourier = randomAlphabetic(12);
        passwordCourier = randomAlphabetic(10);
        firstNameCourier = randomAlphabetic(8);

        Courier newCourier = new Courier(loginCourier, passwordCourier, firstNameCourier);

        courierSteps.createCourier(newCourier)
                .then()
                .statusCode(SC_CREATED)
                .body("ok", is(true));

        CourierCredentials creds = new CourierCredentials(loginCourier, passwordCourier);
        courierId = courierSteps.loginCourier(creds).then().extract().path("id");
    }

    @Test
    public void createCourierTrueWithoutFirstName() {
        loginCourier = randomAlphabetic(12);
        passwordCourier = randomAlphabetic(10);

        Courier newCourier = new Courier(loginCourier, passwordCourier, "");

        courierSteps.createCourier(newCourier)
                .then()
                .statusCode(SC_CREATED)
                .body("ok", is(true));

        CourierCredentials creds = new CourierCredentials(loginCourier, passwordCourier);
        courierId = courierSteps.loginCourier(creds).then().extract().path("id");
    }

    @Test
    public void createCourierFalseWithoutLogin() {
        passwordCourier = randomAlphabetic(10);
        firstNameCourier = randomAlphabetic(8);

        Courier newCourier = new Courier("", passwordCourier, firstNameCourier);

        courierSteps.createCourier(newCourier)
                .then()
                .statusCode(SC_BAD_REQUEST)
                .body("message", containsString("Недостаточно данных для создания учетной записи"));
    }

    @Test
    public void createCourierFalseWithoutPassword() {
        loginCourier = randomAlphabetic(12);
        firstNameCourier = randomAlphabetic(8);

        Courier newCourier = new Courier(loginCourier, "", firstNameCourier);

        courierSteps.createCourier(newCourier)
                .then()
                .statusCode(SC_BAD_REQUEST)
                .body("message", containsString("Недостаточно данных для создания учетной записи"));
    }

    @Test
    public void createDoubleCourierFalse() {
        loginCourier = randomAlphabetic(12);
        passwordCourier = randomAlphabetic(10);
        firstNameCourier = randomAlphabetic(8);

        Courier newCourier = new Courier(loginCourier, passwordCourier, firstNameCourier);

        courierSteps.createCourier(newCourier)
                .then()
                .statusCode(SC_CREATED)
                .body("ok", is(true));

        CourierCredentials creds = new CourierCredentials(loginCourier, passwordCourier);
        courierId = courierSteps.loginCourier(creds).then().extract().path("id");

        courierSteps.createCourier(newCourier)
                .then()
                .statusCode(SC_CONFLICT)
                .body("message", containsString("Этот логин уже используется"));
    }
}