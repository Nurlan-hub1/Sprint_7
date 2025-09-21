package ru.yandex.praktikum;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import ru.yandex.praktikum.steps.CourierSteps;
import ru.yandex.praktikum.steps.models.Courier;
import ru.yandex.praktikum.steps.models.CourierCredentials;

import static org.apache.http.HttpStatus.*;
import static org.hamcrest.Matchers.notNullValue;

import static org.apache.commons.lang3.RandomStringUtils.randomAlphabetic;

public class CourierLoginTest extends BaseTest {

    private CourierSteps courierSteps;
    private String login;
    private String password;
    private Integer courierId;

    @Before
    public void setUp() {
        courierSteps = new CourierSteps();
        login = randomAlphabetic(12);
        password = randomAlphabetic(10);

        Courier newCourier = new Courier(login, password, randomAlphabetic(8));
        courierSteps.createCourier(newCourier).then().statusCode(SC_CREATED);

        CourierCredentials creds = new CourierCredentials(login, password);
        courierId = courierSteps.loginCourier(creds).then().extract().path("id");
    }

    @After
    public void tearDown() {
        if (courierId != null) {
            courierSteps.deleteCourier(courierId).then().statusCode(SC_OK);
        }
    }

    @Test
    public void courierCanLogin() {
        CourierCredentials creds = new CourierCredentials(login, password);
        courierSteps.loginCourier(creds)
                .then()
                .statusCode(SC_OK)
                .body("id", notNullValue());
    }

    @Test
    public void loginWithWrongPassword() {
        CourierCredentials wrong = new CourierCredentials(login, "wrongPass");
        courierSteps.loginCourier(wrong)
                .then()
                .statusCode(SC_NOT_FOUND);
    }

    @Test
    public void loginWithoutLoginField() {
        // send only password
        CourierCredentials noLogin = new CourierCredentials(null, password);
        courierSteps.loginCourier(noLogin)
                .then()
                .statusCode(SC_BAD_REQUEST);
    }
    @Test
    public void loginWithoutPassword() {
        // отправляем только login, без password
        CourierCredentials noPassword = new CourierCredentials(login, null);
        courierSteps.loginCourier(noPassword)
                .then()
                .statusCode(SC_BAD_REQUEST);
    }
    @Test
    public void loginNonExistingCourier() {
        CourierCredentials nonExist = new CourierCredentials("no_such_user_" + randomAlphabetic(6), "pw");
        courierSteps.loginCourier(nonExist)
                .then()
                .statusCode(SC_NOT_FOUND);
    }
}