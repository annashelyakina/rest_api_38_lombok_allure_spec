package in.reqres;

import models.lombok.*;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static io.qameta.allure.Allure.step;
import static io.restassured.RestAssured.given;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static specs.RegisterSpec.*;

public class RegisterTests extends TestBase{

    @Test
    @DisplayName("Проверка успешной регистрации пользователя с валидными данными")
    void successfulRegisterTest() {
        RegisterBodyLombokModel authData = new RegisterBodyLombokModel();
        authData.setEmail(Constants.validEmail);
        authData.setPassword(Constants.validPassword);

        RegisterResponseLombokModel response = step("Make request", ()->
        given(registerRequestSpec)
                .body(authData)
        .when()
                .post()
        .then()
                .spec(registerResponseSpec)
                .extract().as(RegisterResponseLombokModel.class));

        step("Check response", ()->{
                assertEquals("QpwL5tke4Pnpja7X4", response.getToken());
                assertEquals("4", response.getId());
        });
    }

    @Test
    @DisplayName("Проверка статус кода и сообщения об ошибке в случае невалидного 'api key' для регистрации ")
    void invalidApiKeyTest() {
        RegisterBodyLombokModel authData = new RegisterBodyLombokModel();
        authData.setEmail(Constants.validEmail);
        authData.setPassword(Constants.validPassword);

        InvalidApiKeyLombokModel response = step("Make request", ()->
        given(invalidApiKeyRequestSpec)
                .body(authData)
        .when()
                .post()
        .then()
                .spec(invalidApiKeyResponseSpec)
                .extract().as(InvalidApiKeyLombokModel.class));

        step("Check response", ()->
                assertEquals("Invalid or inactive API key", response.getError()));

    }

    @Test
    @DisplayName("Проверка статус кода и сообщения об ошибке в случае невалидного 'email' для регистрации")
    void invalidEmailTest() {
        RegisterBodyLombokModel authData = new RegisterBodyLombokModel();
        authData.setEmail(Constants.invalidEmail);
        authData.setPassword(Constants.validPassword);

        InvalidEmailLombokModel response = step("Make request", ()->
        given(registerRequestSpec)
                .body(authData)
        .when()
                .post()
        .then()
                .spec(invalidEmailResponseSpec)
                .extract().as(InvalidEmailLombokModel.class));
        step("Check response", ()->
                assertEquals("Note: Only defined users succeed registration", response.getError()));
    }

    @Test
    @DisplayName("Проверка статус кода и сообщения об ошибке в случае отсутствия 'email' для регистрации")
    void missingEmailForRegisterTest() {
        RegisterBodyLombokModel authData = new RegisterBodyLombokModel();
        authData.setPassword(Constants.validPassword);

        MissingEmailLombokModel  response = step("Make request", ()->
        given(registerRequestSpec)
                .body(authData)
        .when()
                .post()
        .then()
                .spec(missingEmailResponseSpec)
                .extract().as(MissingEmailLombokModel.class));
        step("Check response", ()->
                assertEquals("Missing email or username", response.getError()));
    }

    @Test
    @DisplayName("Проверка статус кода и сообщения об ошибке в случае отсутствия 'password' для регистрации")
    void missingPasswordForRegisterTest() {
        RegisterBodyLombokModel authData = new RegisterBodyLombokModel();
        authData.setEmail(Constants.validEmail);

        MissingPasswordModel  response = step("Make request", ()->
        given(registerRequestSpec)
                .body(authData)
        .when()
                .post()
        .then()
                .spec(missingPasswordResponseSpec)
                .extract().as(MissingPasswordModel.class));
        step("Check response", ()->
                assertEquals("Missing password", response.getError()));
    }
}
