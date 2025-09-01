package openAPI.reqresIn.tests;

import openAPI.reqresIn.model.*;
import openAPI.reqresIn.specs.Specifications;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;
import static io.restassured.http.ContentType.JSON;
import static openAPI.reqresIn.halpers.Endpoints.*;
import static openAPI.reqresIn.specs.Specifications.*;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.is;
import static org.junit.jupiter.api.Assertions.assertAll;

public class ReqResInTests {

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
    @DisplayName("Проверка списка пользователей. GET.")
    void getListUsersTest() {

        // Предусловие упаковано requestSpec
        ListUsersModel listUsers = given()

                /*
                 Можно обернуть в спецификацию requestSpec
                .baseUri("https://reqres.in/api/")
                .log().uri()
                .log().headers()
                .log().body()
                и начать тест со строчки:
                ListUsersModel listUsers = given(requestSpec)
                 */

                .baseUri(baseUrl)
                .log().uri()
                .log().headers()
                .log().body()
                .contentType(JSON)

                .log().all()
                // Действие
                .when()
                .get("/users?page=2")
                // Проверка
                .then()
                .statusCode(200)
                // Проверка логов
                .log().body()

                // извлечь в pojo класс ListUsersModel
                .extract().as(ListUsersModel.class);


        assertAll(
                () -> assertThat(listUsers.getPage()).isEqualTo(2),
                () -> assertThat(listUsers.getPerPage()).isEqualTo(6),
                () -> assertThat(listUsers.getTotal()).isEqualTo(12),
                () -> assertThat(listUsers.getTotalPages()).isEqualTo(2));
    }

    // Перед запуском убедиться что удаляемый пользователь был создан и был получен запросом FET
    @Test
    @DisplayName("Удаление пользователя. DELETE")
    void deleteUserTest() {

        given()
                // Предусловие
                .baseUri(baseUrl)
                .contentType("application/json")
                .log().all()
                // Действие
                .when()
                .delete("/users/2")
                // Проверка
                .then()
                .statusCode(204);
    }

    @Test
    @DisplayName("Запрос GET SINGLE USERS.")
    void getSingleUsers() {
        // Предусловие
        UserData data = Specifications.requestSpec
                // Действие
                .when()
                .get(SINGLE_USER)
                // Проверка
                .then()
                .spec(responseStatus200)
                .spec(responseBodySpec)
                .extract().as(UserData.class);

        assertAll("Проверка полученных полей пользователя",
                () -> assertThat(data.getUser().getId()).isEqualTo(2),
                () -> assertThat(data.getUser().getEmail()).isEqualTo("janet.weaver@reqres.in"),
                () -> assertThat(data.getUser().getFirstName()).isEqualTo("Janet"),
                () -> assertThat(data.getUser().getLastName()).isEqualTo("Weaver")
        );
    }

    @Test
    @DisplayName("Запрос POST CREATE")
    void postCreateTest() {
        // Предусловие
        CreateRequestModel bodyData = new CreateRequestModel();
        bodyData.setJob("leader");
        bodyData.setName("morpheus");

        CreateResponseModel response = given(requestSpec)
                .body(bodyData)
                // Действие
                .when()
                .post(USERS)
                // Проверка
                .then()
                .spec(responseStatus201)
                .spec(responseBodySpec)
                .extract().as(CreateResponseModel.class);

        assertThat(response.getName()).isEqualTo("morpheus");
        assertThat(response.getJob()).isEqualTo("leader");
        assertThat(response.getId()).isEqualTo("678");
        assertThat(response.getCreatedAt()).isEqualTo("2025-02-13T11:35:50.682Z");
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
    @DisplayName("Запрос PUT")
    void putUpdateTest() {

        // Создание объекта
        CreateRequestModel bodyUpdateData = new CreateRequestModel();
        bodyUpdateData.setJob("zion resident");
        bodyUpdateData.setName("morpheus");

        UpdateResponseModel response = given()
                // Предусловие
                .baseUri(baseUrl)
                .contentType(JSON)
                .body(bodyUpdateData)
                .header("x-api-key", "reqres-free-v1")
                .log().all()
                // Действие
                .when()
                .put("users/2")
                // Проверка
                .then()
                .extract().as(UpdateResponseModel.class);

        assertThat(response.getName()).isEqualTo("morpheus");
        assertThat(response.getJob()).isEqualTo("zion resident");
    }
}
