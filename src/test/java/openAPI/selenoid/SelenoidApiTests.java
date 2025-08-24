package openAPI.selenoid;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.hasKey;
import static org.hamcrest.Matchers.is;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class SelenoidApiTests {

    private String baseUrl = "https://selenoid.autotests.cloud/status";

    @Test
    @DisplayName("Прверить доступности сервиса https://selenoid.autotests.cloud/status")
    void checkTotalWithLogs() {
        given()
                .log().all()
                .when()
                .get(baseUrl)
                .then()
                .log().all()
                .statusCode(200)
                .body("total", is(5));
    }

    @Test
    @DisplayName("Тест с логами .uri(), body() ")
    void checkTotalWithSomeLogs() {
        given()
                .log().uri()
                .when()
                .get(baseUrl)
                .then()
                .log().body()
                .statusCode(200)
                .body("total", is(20));
    }

    @Test
    @DisplayName("Проверка что browsers.chrome = 100.0")
    void checkChromeVersions() {
        given()
                .log().uri()
                .when()
                .get(baseUrl)
                .then()
                .log().body()
                .statusCode(200)
                .body("browsers.chrome", hasKey("100.0"));
    }

    @Test
    @DisplayName("Проверка атрибута total")
    void checkResponseTotal() {

        Integer expectedTotal = 20;

        Integer actualTotal = given()
                .log().uri()
                .when()
                .get(baseUrl)
                .then()
                .log().body()
                .statusCode(200)
                .extract().path("total");

        assertEquals(expectedTotal, actualTotal);
    }

    @Test
    @DisplayName("Проверка ошибки 401")
    void checkWdHubStatus401() {
        given()
                .log().all()
                .when()
                .get(baseUrl)
                .then()
                .log().all()
                .statusCode(401);
    }

    @Test
    @DisplayName("Проверка авторизованного пользователя")
    void checkWdHubWitsAuthStatus() {
        given()
                .log().all()
                .auth().basic("user1", "1234")
                .when()
                .get(baseUrl)
                .then()
                .log().all()
                .statusCode(200)
                .body("value.ready", is(true));
    }
}
