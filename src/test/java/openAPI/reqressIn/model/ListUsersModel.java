package openAPI.reqressIn.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.ArrayList;

@Data
public class ListUsersModel {
    Integer page;
    @JsonProperty("per_page")
    Integer perPage;
    Integer total;
    @JsonProperty("total_pages")
    Integer totalPages;
    ArrayList <Object> data;
    Object support;
}
