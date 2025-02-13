package openAPI.reqressIn.tests;

import jdk.jfr.Description;
import openAPI.reqressIn.model.create.CreateRequestModel;
import openAPI.reqressIn.model.create.CreateResponseModel;
import openAPI.reqressIn.model.listUsers.ListUsersModel;
import openAPI.reqressIn.model.lombok.LoginRequestModel;
import openAPI.reqressIn.model.lombok.LoginResponseModel;
import openAPI.reqressIn.model.singleUsers.UserData;
import openAPI.reqressIn.specs.Specifications;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;
import static io.restassured.http.ContentType.JSON;
import static openAPI.reqressIn.halpers.Endpoints.*;
import static openAPI.reqressIn.specs.Specifications.*;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.is;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class ReqressInTests {
    @Description("Запрос GET LIST USERS.")
    @Test
    void getListUsersTest() {
        ListUsersModel listUsers = given(requestListUsersSpec)
                .when()
                .get(LIST_USERS)
                .then()
                .spec(responseListUsersSpec)
                .extract().as(ListUsersModel.class);


        //Прописать проверку остальных полей массива
        assertThat(listUsers.getPage()).isEqualTo(2);
        assertThat(listUsers.getPerPage()).isEqualTo(6);
        assertThat(listUsers.getTotal()).isEqualTo(12);
        assertThat(listUsers.getTotalPages()).isEqualTo(2);
    }

    @Description("Запрос GET SINGLE USERS.")
    @Test
    void singleUsers() {
        UserData data = Specifications.requestSingleUsersSpec
                .when()
                .get(SINGLE_USER)
                .then()
                .spec(responseStatus200)
                .log().body()
                .extract().as(UserData.class);

        assertEquals(2, data.getUser().getId());
        assertEquals("janet.weaver@reqres.in", data.getUser().getEmail());
        assertEquals("Janet", data.getUser().getFirstName());
        assertEquals("Weaver", data.getUser().getLastName());
    }

    @Description("Запрос POST CREATE")
    @Test
    void postCreateTest() {
        CreateRequestModel bodyData = new CreateRequestModel();
        bodyData.setJob("leader");
        bodyData.setName("morpheus");

        CreateResponseModel responseModel = given(requestCreateSpec)
                .body(bodyData)
                .when()
                .post(USERS)
                .then()
                .spec(responseCreateSpec)
                .extract().as(CreateResponseModel.class);

        //Прописать методы проверки
    }

    @Description("Запрос POST LOGIN OK. Авторизация")
    @Test
    void loginWithSpecsTest() {
        LoginRequestModel loginBody = new LoginRequestModel();
        loginBody.setEmail("eve.holt@reqres.in");
        loginBody.setPassword("cityslicka");

        LoginResponseModel response = given(requestLoginSpec)
                .body(loginBody)
                .when()
                .post("/login")
                .then()
                .spec(responseLoginSpec)
                .extract().as(LoginResponseModel.class);

        assertThat(response.getToken()).isEqualTo("QpwL5tke4Pnpja7X4");
    }

    @Description("Авторизации без логина и пароля")
    @Test
    void loginErrorTest() {
        String data = "{ \"email\": \"eve.holt@reqres.in\"}";

        given()
                .log().all()
                .contentType(JSON)
                .body(data)
                .when()
                .post("https://reqres.in/api")
                .then()
                .log().body()
                .statusCode(400)
                .body("error", is("Missing password"));
    }
}

