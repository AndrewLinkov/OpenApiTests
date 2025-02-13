package openAPI.reqressIn.tests;


import lombok.Data;
import openAPI.reqressIn.model.singleUsers.UserData;
import openAPI.reqressIn.specs.SingleUsersSpec;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class SingleUserTests {
    @Test
    void singleUsers() {
        UserData data = SingleUsersSpec.request
                .when()
                .get("/users/2")
                .then()
                .spec(SingleUsersSpec.responseStatus)
                .log().body()
                .extract().as(UserData.class);


        assertEquals(2, data.getUser().getId());
        assertEquals("janet.weaver@reqres.in", data.getUser().getEmail());
        assertEquals("Janet", data.getUser().getFirstName());
        assertEquals("Weaver", data.getUser().getLastName());
    }
}
