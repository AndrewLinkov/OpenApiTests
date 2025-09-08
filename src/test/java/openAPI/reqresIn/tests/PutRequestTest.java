package openAPI.reqresIn.tests;

import openAPI.reqresIn.model.CreateRequestModel;
import openAPI.reqresIn.model.UpdateResponseModel;
import openAPI.reqresIn.model.UserData;
import org.assertj.core.api.AssertionsForClassTypes;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;
import static io.restassured.http.ContentType.JSON;
import static openAPI.reqresIn.tests.BaseTest.BASE_URL;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

public class PutRequestTest {


    @Test
    @DisplayName("Проверка наличия пользователя перед удалением.")
    void getUserTest() {

        int userId = 2;

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
                () -> AssertionsForClassTypes.assertThat(userData.getUser().getId()).isEqualTo(2),
                () -> AssertionsForClassTypes.assertThat(userData.getUser().getEmail()).isEqualTo("janet.weaver@reqres.in"),
                () -> AssertionsForClassTypes.assertThat(userData.getUser().getFirstName()).isEqualTo("Janet"),
                () -> AssertionsForClassTypes.assertThat(userData.getUser().getLastName()).isEqualTo("Weaver")
        );
    }

    @Test
    @DisplayName("Запрос PUT")
    void putUpdateTest() {

        int userId = 2;

        CreateRequestModel bodyUpdateData = new CreateRequestModel();
        bodyUpdateData.setJob("zion resident");
        bodyUpdateData.setName("morpheus");

        UpdateResponseModel response = given()
                // Предусловие
                .baseUri(BASE_URL)
                .contentType(JSON)
                .body(bodyUpdateData)
                .header("x-api-key", "reqres-free-v1")
                .log().all()
                // Действие
                .when()
                .put("users/" + userId)
                // Проверка
                .then()
                .extract().as(UpdateResponseModel.class);

        assertThat(response.getName()).isEqualTo("morpheus");
        assertThat(response.getJob()).isEqualTo("zion resident");
    }
}
