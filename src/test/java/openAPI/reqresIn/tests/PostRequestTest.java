package openAPI.reqresIn.tests;

import openAPI.reqresIn.model.CreateRequestModel;
import openAPI.reqresIn.model.CreateResponseModel;
import openAPI.reqresIn.model.LoginRequestModel;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;
import static io.restassured.http.ContentType.JSON;
import static openAPI.reqresIn.halpers.Endpoints.USERS;
import static openAPI.reqresIn.specs.Specifications.*;
import static openAPI.reqresIn.tests.BaseTest.BASE_URL;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.is;

public class PostRequestTest {

    private final String baseUrl = "https://reqres.in/api";

    @Test
    @DisplayName("Авторизации пользователя. POST.")
    void postLoginTest() {

        // Если запрос POST создаем Pojo класс
        LoginRequestModel loginRequestModel = new LoginRequestModel();
        loginRequestModel.setUsername("");
        loginRequestModel.setPassword("cityslicka");
        loginRequestModel.setEmail("eve.holt@reqres.in");

        String response = given()
                // Предусловие
                .baseUri(baseUrl)
                .contentType("application/json")
                .header("x-api-key", "reqres-free-v1")
                .body(loginRequestModel)
                .log().all()
                // Действие
                .when()
                .post("/login")
                // Проверка
                .then()
                .log().body()
                .statusCode(200)
                .extract().asString();

        // Вывод в консоль
        System.out.println("Успешный ответ: " + response);

        // Проверяем, что в ответе есть токен
        assertThat(response).contains("token");
    }


    @Test
    @DisplayName("Проверка авторизации без логина и пароля. POST.")
    void postLoginErrorTest() {
        // Предусловие
        String data = "{ \"email\": \"eve.holt@reqres.in\"}";

        given()
                .contentType(JSON)
                .header("x-api-key", "reqres-free-v1")
                .body(data)
                // Действие
                .when()
                .post("https://reqres.in/api")
                // Проверка
                .then()
                .log().body()
                .statusCode(401)
                .body("error", is("Missing password"));
    }

    @Test
    @DisplayName("Запрос POST CREATE")
    void postCreateTest() {

        // Предусловие
        CreateRequestModel bodyData = new CreateRequestModel();
        bodyData.setJob("leader");
        bodyData.setName("morpheus");

        CreateResponseModel response = given

                .baseUri(BASE_URL)
                .body(bodyData)
                // Действие
                .when()
                .post("users/2")
                // Проверка
                .then()
                .spec(responseStatus201Spes)
                .spec(responseBodySpec)
                .extract().as(CreateResponseModel.class);

        assertThat(response.getName()).isEqualTo("morpheus");
        assertThat(response.getJob()).isEqualTo("leader");
        assertThat(response.getId()).isEqualTo("678");
        assertThat(response.getCreatedAt()).isEqualTo("2025-02-13T11:35:50.682Z");
    }
}
