package openAPI.reqressIn.tests;

import jdk.jfr.Description;
import openAPI.reqressIn.model.create.CreateRequestModel;
import openAPI.reqressIn.model.create.CreateResponseModel;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;
import static openAPI.reqressIn.specs.CreateSpec.requestCreateSpec;
import static openAPI.reqressIn.specs.CreateSpec.responseCreateSpec;

public class PostCreateTests {

    @Description("Тестовый метод проверки запроса POST")
    @Test
    void postCreateTest() {
        CreateRequestModel bodyData = new CreateRequestModel();
        bodyData.setJob("leader");
        bodyData.setName("morpheus");

        CreateResponseModel responseModel = given(requestCreateSpec)
                .body(bodyData)
                .when()
                .post("/users")
                .then()
                .spec(responseCreateSpec)
                .extract().as(CreateResponseModel.class);

        //Прописать методы проверки
    }
}
