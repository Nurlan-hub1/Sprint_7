package ru.yandex.praktikum;

import io.restassured.RestAssured;
import io.restassured.parsing.Parser;
import org.junit.BeforeClass;

public class BaseTest {
    @BeforeClass
    public static void globalSetUp() {
        RestAssured.defaultParser = Parser.JSON;
    }
}