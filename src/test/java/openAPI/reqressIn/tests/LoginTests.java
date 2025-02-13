package openAPI.reqressIn.tests;

import jdk.jfr.Description;
import openAPI.reqressIn.model.lombok.LoginRequestModel;
import openAPI.reqressIn.model.lombok.LoginResponseModel;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;
import static io.restassured.http.ContentType.JSON;
import static openAPI.reqressIn.specs.LoginSpec.loginRequestSpec;
import static openAPI.reqressIn.specs.LoginSpec.loginResponseSpec;
import static org.assertj.core.api.Assertions.assertThat;

public class LoginTests {
    @Test
    void loginWithLombokModelTest() {
        LoginRequestModel loginBody = new LoginRequestModel();
        loginBody.setEmail("eve.holt@reqres.in");
        loginBody.setPassword("cityslicka");

        LoginResponseModel response = given()
                .log().uri()
                .log().body()
                .contentType(JSON)
                .body(loginBody)
                .when()
                .post("https://reqres.in/api/login")
                .then()
                .log().status()
                .log().body()
                .statusCode(200)
                .extract().as(LoginResponseModel.class);

        assertThat(response.getToken()).isEqualTo("QpwL5tke4Pnpja7X4");
    }

    @Description("Тестовый метод со спецификацией")
    @Test
    void loginWithSpecsTest() {
        LoginRequestModel loginBody = new LoginRequestModel();
        loginBody.setEmail("eve.holt@reqres.in");
        loginBody.setPassword("cityslicka");

        LoginResponseModel response = given(loginRequestSpec)
                .body(loginBody)
                .when()
                .post("/login")
                .then()
                .spec(loginResponseSpec)
                .extract().as(LoginResponseModel.class);

        assertThat(response.getToken()).isEqualTo("QpwL5tke4Pnpja7X4");
    }
}
