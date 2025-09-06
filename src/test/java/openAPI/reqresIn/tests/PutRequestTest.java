package openAPI.reqresIn.tests;

import openAPI.reqresIn.model.CreateRequestModel;
import openAPI.reqresIn.model.UpdateResponseModel;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;
import static io.restassured.http.ContentType.JSON;
import static openAPI.reqresIn.tests.BaseTest.BASE_URL;
import static org.assertj.core.api.Assertions.assertThat;

public class PutRequestTest {

    //private final String baseUrl = "https://reqres.in/api";

    @Test
    @DisplayName("Запрос PUT")
    void putUpdateTest() {

        // Создание объекта
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
                .put("users/2")
                // Проверка
                .then()
                .extract().as(UpdateResponseModel.class);

        assertThat(response.getName()).isEqualTo("morpheus");
        assertThat(response.getJob()).isEqualTo("zion resident");
    }
}
