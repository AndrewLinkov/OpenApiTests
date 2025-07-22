package openAPI.reqressIn.tests;

import openAPI.reqressIn.model.CreateRequestModel;
import openAPI.reqressIn.model.CreateResponseModel;
import openAPI.reqressIn.model.ListUsersModel;
import openAPI.reqressIn.model.LoginRequestModel;
import openAPI.reqressIn.model.LoginResponseModel;
import openAPI.reqressIn.model.UserData;
import openAPI.reqressIn.specs.Specifications;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;
import static io.restassured.http.ContentType.JSON;
import static openAPI.reqressIn.halpers.Endpoints.*;
import static openAPI.reqressIn.specs.Specifications.*;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.is;
import static org.junit.jupiter.api.Assertions.assertAll;

public class ReqressInTests {

    @Test
    @DisplayName("������ GET LIST USERS.")
    void getListUsersTest() {
        // ����������
        ListUsersModel listUsers = given(requestSpec)
                // ��������
                .when()
                .get(LIST_USERS)
                // ��������
                .then()
                .spec(responseStatus200)
                .spec(responseBodySpec)
                // ������� � pojo ����� ListUsersModel
                .extract().as(ListUsersModel.class);

        assertAll(
                () -> assertThat(listUsers.getPage()).isEqualTo(2),
                () -> assertThat(listUsers.getPerPage()).isEqualTo(6),
                () -> assertThat(listUsers.getTotal()).isEqualTo(12),
                () -> assertThat(listUsers.getTotalPages()).isEqualTo(2));
    }

    @Test
    @DisplayName("������ GET SINGLE USERS.")
    void singleUsers() {
        // �����������
        UserData data = Specifications.requestSpec
                // ��������
                .when()
                .get(SINGLE_USER)
                // ��������
                .then()
                .spec(responseStatus200)
                .spec(responseBodySpec)
                .extract().as(UserData.class);

        // ���������� �������� ����� ������
        assertAll("�������� ���������� ����� ������������",
                () -> assertThat(data.getUser().getId()).isEqualTo(2),
                () -> assertThat(data.getUser().getEmail()).isEqualTo("janet.weaver@reqres.in"),
                () -> assertThat(data.getUser().getFirstName()).isEqualTo("Janet"),
                () -> assertThat(data.getUser().getLastName()).isEqualTo("Weaver")
        );
    }

    @Test
    @DisplayName("������ POST CREATE")
    void postCreateTest() {
        // �����������
        CreateRequestModel bodyData = new CreateRequestModel();
        bodyData.setJob("leader");
        bodyData.setName("morpheus");

        CreateResponseModel response = given(requestSpec)
                .body(bodyData)
                // ��������
                .when()
                .post(USERS)
                // ��������
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
    @DisplayName("������ POST LOGIN OK. �����������")
    void loginWithSpecsTest() {
        // �����������
        LoginRequestModel loginBody = new LoginRequestModel();
        loginBody.setEmail("eve.holt@reqres.in");
        loginBody.setPassword("cityslicka");

        LoginResponseModel response = given(requestSpec)
                .body(loginBody)
                // ��������
                .when()
                .post(LOGIN)
                // ��������
                .then()
                .spec(responseStatus200)
                .spec(responseBodySpec)
                .extract().as(LoginResponseModel.class);

        assertThat(response.getToken()).isEqualTo("QpwL5tke4Pnpja7X4");
    }

    @Test
    @DisplayName("�������� ����������� ��� ������ � ������ (������ POST)")
    void loginErrorTest() {
        // �����������
        String data = "{ \"email\": \"eve.holt@reqres.in\"}";

        given()
                .log().all()
                .contentType(JSON)
                .body(data)
                // ��������
                .when()
                .post("https://reqres.in/api")
                // ��������
                .then()
                .log().body()
                .statusCode(400)
                .body("error", is("Missing password"));
    }
}
