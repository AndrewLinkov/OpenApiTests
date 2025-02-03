package openAPI.reqressIn;

import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;
import static io.restassured.http.ContentType.JSON;
import static org.hamcrest.Matchers.is;

public class ReqressinApiTests {

    private final String URL = "https://reqres.in/api";
    private final String login = "/login";

    /*
    1. Выполнить запрос POST https://reqres.in/api/login
        с телом запроса: { "email": "eve.holt@reqres.in", "password": "cityslicka" }
    2. Полуить ответ: { "token": "QpwL5tke4Pnpja7X4" }
    3. Проверить токен
     */
    @Test
    void loginTest() {
        String data = "{ \"email\": \"eve.holt@reqres.in\", \"password\": \"cityslicka\" }";

        given()
                .log().all()
                .contentType(JSON)
                .body(data)
                .when()
                .post(URL + login)
                .then()
                .log().all()
                .statusCode(200)
                .body("token", is("QpwL5tke4Pnpja7X4"));
    }

    //Проверка ошибки 415
    @Test
    void negativLoginTest() {
        given()
                .log().all()
                .when()
                .post(URL)
                .then()
                .log().body()
                .statusCode(415);
    }

    //Проверка авторизации без логина и пароля
    @Test
    void loginErrorTest() {
        String data = "{ \"email\": \"eve.holt@reqres.in\"}";

        given()
                .log().all()
                .contentType(JSON)
                .body(data)
                .when()
                .post(URL)
                .then()
                .log().body()
                .statusCode(400)
                .body("error", is("Missing password"));
    }
}
