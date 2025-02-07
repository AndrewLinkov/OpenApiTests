package openAPI.reqressIn.model.listUsers;

import lombok.Data;

import java.util.ArrayList;

@Data
public class ListUsersModel {
    String page;
    String per_page;
    String total;
    String total_pages;
    ArrayList <Object> data;
    Object support;
}
