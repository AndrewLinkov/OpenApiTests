package openAPI.reqressIn.specs;

import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;

import static io.restassured.RestAssured.with;
import static io.restassured.http.ContentType.JSON;

public class SingleUsersSpec {
    public static RequestSpecification request = with()
            .baseUri("https://reqres.in")
            .basePath("/api")
            .log().all()
            .contentType(JSON);

    // Метод проверики статуса 200
    public static ResponseSpecification responseStatus = new ResponseSpecBuilder()
            .expectStatusCode(200)
            .build();
}
