package openAPI.reqresIn.tests;

import openAPI.reqresIn.model.UserData;
import openAPI.reqresIn.specs.Specifications;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static openAPI.reqresIn.specs.Specifications.*;
import static openAPI.reqresIn.tests.BaseTest.BASE_URL;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

public class GetRequestTest {

    @Test
    @DisplayName("Запрос GET SINGLE USERS.")
    void getSingleUsers() {

        // Предусловие
        UserData data = Specifications

                .baseUri(BASE_URL)
                // Действие
                .when()
                .get("")
                // Проверка
                .then()
                .spec(responseStatus200Spec)
                .spec(responseBodySpec)
                .extract().as(UserData.class);

        assertAll("Проверка полученных полей пользователя",
                () -> assertThat(data.getUser().getId()).isEqualTo(2),
                () -> assertThat(data.getUser().getEmail()).isEqualTo("janet.weaver@reqres.in"),
                () -> assertThat(data.getUser().getFirstName()).isEqualTo("Janet"),
                () -> assertThat(data.getUser().getLastName()).isEqualTo("Weaver")
        );
    }
}
