package openAPI.selenoid;

import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.hasKey;
import static org.hamcrest.Matchers.is;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class selenoidApiTests {

    // �������� ����������� "https://selenoid.autotests.cloud/status"
    @Test
    void checkTotalWithLogs() {
        given()
                .log().all()
                .when()
                .get("https://selenoid.autotests.cloud/status")
                .then()
                .log().all()
                .statusCode(200)
                .body("total", is(20));
    }

    //���� � ������ .uri(), body()
    @Test
    void checkTotalWithSomeLogs() {
        given()
                .log().uri()
                .when()
                .get("https://selenoid.autotests.cloud/status")
                .then()
                .log().body()
                .statusCode(200)
                .body("total", is(20));
    }

    //�������� ��� browsers.chrome = 100.0
    @Test
    void checkChromeVersions() {
        given()
                .log().uri()
                .when()
                .get("https://selenoid.autotests.cloud/status")
                .then()
                .log().body()
                .statusCode(200)
                .body("browsers.chrome", hasKey("100.0"));
    }

    //�������� total ����� assert
    @Test
    void checkResponseTotal() {
        Integer expectedTotal = 20;

        Integer actualTotal = given()
                .log().uri()
                .when()
                .get("https://selenoid.autotests.cloud/status")
                .then()
                .log().body()
                .statusCode(200)
                .extract().path("total");

        assertEquals(expectedTotal, actualTotal);
    }

    /*
    �������� ������ https://selenoid.autotests.cloud/wd/hub/status
    ���������������� ������������ �������� 401 ������
     */
    @Test
    void checkWdHubStatus401() {
        given()
                .log().all()
                .when()
                .get("https://selenoid.autotests.cloud/wd/hub/status")
                .then()
                .log().all()
                .statusCode(401);
    }


    /*
    �������� ������ https://selenoid.autotests.cloud/wd/hub/status
    ������������ �����������. ��� ������ �������� �����: user1 ������: 1234
     */
    @Test
    void checkWdHubWitsAuthStatus() {
        given()
                .log().all()
                .auth().basic("user1", "1234")
                .when()
                .get("https://selenoid.autotests.cloud/wd/hub/status")
                .then()
                .log().all()
                .statusCode(200)
                .body("value.ready", is(true));
    }
}
