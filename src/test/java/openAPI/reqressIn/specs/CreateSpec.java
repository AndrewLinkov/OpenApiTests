package openAPI.reqressIn.specs;

import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;

import static io.restassured.RestAssured.with;
import static io.restassured.filter.log.LogDetail.BODY;
import static io.restassured.filter.log.LogDetail.STATUS;
import static io.restassured.http.ContentType.JSON;

public class CreateSpec {
    public static RequestSpecification requestCreateSpec = with()
            .log().uri()
            .log().headers()
            .log().body()
            .contentType(JSON)
            .baseUri("https://reqres.in")
            .basePath("api");

    public static ResponseSpecification responseCreateSpec = new ResponseSpecBuilder()
            .log(STATUS)
            .log(BODY)
            .build();
}

