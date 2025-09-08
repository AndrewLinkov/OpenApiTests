package openAPI.reqresIn.tests;

import openAPI.reqresIn.model.UserData;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;
import static openAPI.reqresIn.tests.BaseTest.BASE_URL;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

public class DeleteRequestTest {

    @Test
    @DisplayName("Проверка наличия пользователя перед удалением.")
    void getUserTest() {

        int userId = 3;

        UserData userData = given()
                // Предусловие
                .baseUri(BASE_URL)
                .header("x-api-key", "reqres-free-v1")
                .log().all()
                // Действие
                .when()
                .get("users/" + userId)
                // Проверка
                .then()
                .statusCode(200)
                .log().body()
                .extract().as(UserData.class);

        assertAll(
                () -> assertThat(userData.getUser().getId()).isEqualTo(2),
                () -> assertThat(userData.getUser().getEmail()).isEqualTo("janet.weaver@reqres.in"),
                () -> assertThat(userData.getUser().getFirstName()).isEqualTo("Janet"),
                () -> assertThat(userData.getUser().getLastName()).isEqualTo("Weaver")
        );
    }

    @Test
    @DisplayName("DELETE - Удаление пользователя")
    void deleteUserTest() {

        int userId = 3;

        given()
                .baseUri(BASE_URL)
                .header("x-api-key", "reqres-free-v1")
                .contentType("application/json")
                .log().body()
                .when()
                .delete("users/" + userId)
                .then()
                .statusCode(204)
                .log().all();

        given()
                .baseUri(BASE_URL)
                .header("x-api-key", "reqres-free-v1")
                .when()
                .get("users/" + userId)
                .then()
                .statusCode(200);
    }
}
