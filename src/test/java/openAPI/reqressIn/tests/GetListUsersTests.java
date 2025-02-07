package openAPI.reqressIn.tests;

import jdk.jfr.Description;
import openAPI.reqressIn.model.listUsers.ListUsersModel;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;
import static openAPI.reqressIn.specs.ListUsersSpec.requestListUsersSpec;
import static openAPI.reqressIn.specs.ListUsersSpec.responseListUsersSpec;
import static org.assertj.core.api.Assertions.assertThat;

public class GetListUsersTests {

    @Description("ѕолучение данных по пользователю")
    @Test
    void getListUsersTest() {
        ListUsersModel listUsers = given(requestListUsersSpec)
                .when()
                .get("/users?page=2")
                .then()
                .spec(responseListUsersSpec)
                .extract().as(ListUsersModel.class);

        //ѕрописать проверку остальных полей массива
        assertThat(listUsers.getPage()).isEqualTo("2");
        assertThat(listUsers.getPer_page()).isEqualTo("6");
        assertThat(listUsers.getTotal()).isEqualTo("12");
        assertThat(listUsers.getTotal_pages()).isEqualTo("2");
    }
}
