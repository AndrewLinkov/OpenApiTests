package openAPI.reqressIn.specs;

import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;

import static io.restassured.RestAssured.with;
import static io.restassured.filter.log.LogDetail.BODY;
import static io.restassured.http.ContentType.JSON;

public class Specifications {
    public static ResponseSpecification responseStatus200 = new ResponseSpecBuilder()
            .expectStatusCode(200)
            .build();

    public static ResponseSpecification responseStatus201 = new ResponseSpecBuilder()
            .expectStatusCode(201)
            .build();

    public static ResponseSpecification responseStatus400 = new ResponseSpecBuilder()
            .expectStatusCode(400)
            .build();

    public static ResponseSpecification responseBodySpec = new ResponseSpecBuilder()
            .log(BODY)
            .build();

    public static RequestSpecification requestSpec = with()
            .baseUri("https://reqres.in/api")
            .log().uri()
            .log().headers()
            .log().body()
            .contentType(JSON);
}
