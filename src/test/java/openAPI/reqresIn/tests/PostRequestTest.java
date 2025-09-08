package openAPI.reqresIn.tests;

import openAPI.reqresIn.model.CreateRequestModel;
import openAPI.reqresIn.model.CreateResponseModel;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.io.File;
import static io.restassured.RestAssured.given;
import static io.restassured.http.ContentType.JSON;
import static openAPI.reqresIn.specs.Specifications.*;
import static openAPI.reqresIn.tests.BaseTest.BASE_PATH_JSON;
import static openAPI.reqresIn.tests.BaseTest.BASE_URL;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.is;

public class PostRequestTest {

    @Test
    @DisplayName("Создание пользователя. Пользователь прописан в json файле. POST.")
    void postAddUserTest() {

        File jsonBody = new File(BASE_PATH_JSON + "addNewUser.json");

        String token = given()
                .baseUri(BASE_URL)
                .contentType(JSON)
                .header("x-api-key", "reqres-free-v1")
                .body(jsonBody)
                .log().body()
                .when()
                .post("login")
                .then()
                .log().body()
                .statusCode(200)
                // Извлекаем токен
                .extract().asString();

        // Проверяем, что в ответе есть токен
        assertThat(token).contains("token");
    }
}