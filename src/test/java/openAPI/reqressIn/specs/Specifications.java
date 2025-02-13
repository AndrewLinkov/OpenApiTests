package openAPI.reqressIn.specs;

import io.restassured.RestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;

import static io.restassured.RestAssured.with;
import static io.restassured.filter.log.LogDetail.BODY;
import static io.restassured.filter.log.LogDetail.STATUS;
import static io.restassured.http.ContentType.JSON;
import static org.hamcrest.Matchers.notNullValue;

public class Specifications {
    public static ResponseSpecification responseStatus200 = new ResponseSpecBuilder()
                .expectStatusCode(200)
                .build();

    public static RequestSpecification requestListUsersSpec = with()
            .baseUri("https://reqres.in")
            .basePath("/api")
            .log().uri()
            .log().headers()
            .log().body()
            .contentType(JSON);

    public static ResponseSpecification responseListUsersSpec = new ResponseSpecBuilder()
            .log(STATUS)
            .log(BODY)
            .build();

    public static RequestSpecification requestSingleUsersSpec = with()
            .baseUri("https://reqres.in")
            .basePath("/api")
            .log().all()
            .contentType(JSON);

    public static RequestSpecification requestCreateSpec = with()
            .baseUri("https://reqres.in")
            .basePath("api")
            .log().uri()
            .log().headers()
            .log().body()
            .contentType(JSON);

    public static ResponseSpecification responseCreateSpec = new ResponseSpecBuilder()
            .log(STATUS)
            .log(BODY)
            .build();

    public static RequestSpecification requestLoginSpec = with()
            .baseUri("https://reqres.in")
            .basePath("/api")
            .log().uri()
            .log().headers()
            .log().body()
            .contentType(JSON);

    public static ResponseSpecification responseLoginSpec = new ResponseSpecBuilder()
            .log(STATUS)
            .log(BODY)
            .expectStatusCode(200)
            .expectBody("token", notNullValue())
            .build();

}
