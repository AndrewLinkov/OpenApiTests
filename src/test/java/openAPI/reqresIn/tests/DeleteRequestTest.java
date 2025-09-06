package openAPI.reqresIn.tests;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;
import static openAPI.reqresIn.tests.BaseTest.BASE_URL;

public class DeleteRequestTest {

    // ����� �������� ��������� ��� ��������� ������������ ��� ������
    // � ��� ������� �������� GET
    @Test
    @DisplayName("�������� ������������. DELETE")
    void deleteUserTest() {

        given()
                // �����������
                .baseUri(BASE_URL)
                .contentType("application/json")
                .log().all()
                // ��������
                .when()
                .delete("users/2")
                // ��������
                .then()
                .statusCode(204);
    }
}
