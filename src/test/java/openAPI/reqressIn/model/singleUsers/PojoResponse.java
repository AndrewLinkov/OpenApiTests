package openAPI.reqressIn.model.singleUsers;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data

public class PojoResponse {

    private Data data;
    private Support support;

    class Data {
        private Integer id;
        private String email;
        @JsonProperty("first_name")
        private String firstName;
        @JsonProperty("last_name")
        private String lastName;
    }

    class Support {
        public String url;
        public String text;
    }
}