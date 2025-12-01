package in.reqres;

import models.lombok.*;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import static io.qameta.allure.Allure.step;
import static io.restassured.RestAssured.given;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static specs.RegisterSpec.*;

@Tag("api_tests")
public class ApiTests  extends TestBase{

    @Test
    @DisplayName("Проверка успешной регистрации пользователя с валидными данными")
    void successfulRegisterTest() {
        RegisterBodyLombokModel authData = new RegisterBodyLombokModel();
        authData.setEmail(Constants.VALID_EMAIL);
        authData.setPassword(Constants.VALID_PASSWORD);

        RegisterResponseLombokModel response = step("Make request", ()->
        given(registerRequestSpec)
                .body(authData)
        .when()
                .post("/register")
        .then()
                .spec(responseSpec200)
                .extract().as(RegisterResponseLombokModel.class));

        step("Check response", ()->{
                assertThat(response.getToken(), is(notNullValue()));
                assertThat(response.getToken().length(), is(greaterThanOrEqualTo(17)));
                assertThat(response.getToken(), is(matchesRegex("\\S[a-zA-Z0-9]*\\S")));
                assertEquals("QpwL5tke4Pnpja7X4", response.getToken());
                assertEquals("4", response.getId());
        });
    }

    @Test
    @DisplayName("Проверка статус кода и сообщения об ошибке в случае невалидного 'api key' для регистрации ")
    void invalidApiKeyTest() {
        RegisterBodyLombokModel authData = new RegisterBodyLombokModel();
        authData.setEmail(Constants.VALID_EMAIL);
        authData.setPassword(Constants.VALID_PASSWORD);

        InvalidApiErrorResponceLombokModel response = step("Make request", ()->
        given(invalidApiKeyRequestSpec)
                .body(authData)
        .when()
                .post("/register")
        .then()
                .spec(responseSpec403)
                .extract().as(InvalidApiErrorResponceLombokModel.class));

        step("Check response", ()->
                assertEquals("invalid_api_key", response.getError()));

    }

    @Test
    @DisplayName("Проверка статус кода и сообщения об ошибке в случае невалидного 'email' для регистрации")
    void invalidEmailTest() {
        RegisterBodyLombokModel authData = new RegisterBodyLombokModel();
        authData.setEmail(Constants.INVALID_EMAIL);
        authData.setPassword(Constants.VALID_PASSWORD);

        RegisterErrorResponceLombokModel response = step("Make request", ()->
        given(registerRequestSpec)
                .body(authData)
        .when()
                .post("/register")
        .then()
                .spec(responseSpec400)
                .extract().as(RegisterErrorResponceLombokModel.class));
        step("Check response", ()->
                assertEquals("Note: Only defined users succeed registration", response.getError()));
    }

    @Test
    @DisplayName("Проверка статус кода и сообщения об ошибке в случае отсутствия 'email' для регистрации")
    void missingEmailForRegisterTest() {
        RegisterBodyLombokModel authData = new RegisterBodyLombokModel();
        authData.setPassword(Constants.VALID_PASSWORD);

        RegisterErrorResponceLombokModel  response = step("Make request", ()->
        given(registerRequestSpec)
                .body(authData)
        .when()
                .post("/register")
        .then()
                .spec(responseSpec400)
                .extract().as(RegisterErrorResponceLombokModel.class));
        step("Check response", ()->
                assertEquals("Missing email or username", response.getError()));
    }

    @Test
    @DisplayName("Проверка статус кода и сообщения об ошибке в случае отсутствия 'password' для регистрации")
    void missingPasswordForRegisterTest() {
        RegisterBodyLombokModel authData = new RegisterBodyLombokModel();
        authData.setEmail(Constants.VALID_EMAIL);

        RegisterErrorResponceLombokModel  response = step("Make request", ()->
        given(registerRequestSpec)
                .body(authData)
        .when()
                .post("/register")
        .then()
                .spec(responseSpec400)
                .extract().as(RegisterErrorResponceLombokModel .class));
        step("Check response", ()->
                assertEquals("Missing password", response.getError()));
    }

    @Test
    @DisplayName("Проверка получения данных методом GET для существующего пользователя")
    void successfulGetUserTest() {
        GetUserResponseLombokModel response = step("Make request", () ->
                given(getUserRequestSpec)
                        .when()
                        .get("/users/2")
                        .then()
                        .spec(responseSpec200)
                        .extract().as(GetUserResponseLombokModel.class));
        step("Check response", () -> {
            assertEquals("2", response.getData().getId());
            assertEquals("janet.weaver@reqres.in", response.getData().getEmail());
            assertEquals("Janet", response.getData().getFirst_name());
            assertEquals("Weaver", response.getData().getLast_name());
            assertEquals("https://reqres.in/img/faces/2-image.jpg", response.getData().getAvatar());
        });
    }

        @Test
        @DisplayName("Проверка обновления данных методом PUT для существующего пользователя")
        void successfulPutUserTest() {
            UpdateResponseLombokModel authData = new UpdateResponseLombokModel();
            authData.setName(Constants.VALID_NAME);
            authData.setJob(Constants.VALID_JOB);

            UpdateResponseLombokModel response = step("Make request", () ->
                    given(registerRequestSpec)
                            .body(authData)
                            .when()
                            .put("/users/2")
                            .then()
                            .spec(responseSpec200)
                            .extract().as(UpdateResponseLombokModel.class));

            step("Check response", () -> {
                assertEquals("morpheus", response.getName());
                assertEquals("zion resident", response.getJob());
            });
        }

            @Test
            @DisplayName("Проверка обновления данных методом PATCH для существующего пользователя")
            void successfulPatchUserTest() {
                UpdateResponseLombokModel authData = new UpdateResponseLombokModel();
                authData.setName(Constants.VALID_NAME);
                authData.setJob(Constants.VALID_JOB);

                UpdateResponseLombokModel response = step("Make request", ()->
                        given(registerRequestSpec)
                                .body(authData)
                                .when()
                                .patch("/users/2")
                                .then()
                                .spec(responseSpec200)
                                .extract().as(UpdateResponseLombokModel.class));

                step("Check response", ()->{
                    assertEquals("morpheus", response.getName());
                    assertEquals("zion resident", response.getJob());
                });
    }

    @Test
    @DisplayName("Проверка удаления пользователя методом DELETE")
    void successfulDeleteUserTest() {
         step("Make request", ()->
                given(getUserRequestSpec)
                .when()
                        .delete("/users/2")
                .then()
                        .spec(responseSpec204));
    }

}
