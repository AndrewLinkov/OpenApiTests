package openAPI.reqresIn.tests;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;
import static openAPI.reqresIn.tests.BaseTest.BASE_URL;

public class DeleteRequestTest {

    // Перед запуском убедиться что удаляемый пользователь был создан
    // и был получен запросом GET
    @Test
    @DisplayName("Удаление пользователя. DELETE")
    void deleteUserTest() {

        given()
                // Предусловие
                .baseUri(BASE_URL)
                .contentType("application/json")
                .log().all()
                // Действие
                .when()
                .delete("users/2")
                // Проверка
                .then()
                .statusCode(204);
    }
}
