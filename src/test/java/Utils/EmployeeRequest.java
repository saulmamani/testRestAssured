package Utils;

import Constants.EmployeeEndpoint;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;

public class EmployeeRequest {
    public static Response get(String endpoint) {
        RestAssured.baseURI = EmployeeEndpoint.BASE_URL;
        Response response = RestAssured
                .when().get(endpoint);
        return getResponseBody(response);
    }

    public static Response getWithId(String endpoint, String id) {
        RestAssured.baseURI = EmployeeEndpoint.BASE_URL;
        Response response = RestAssured
                .given().pathParam("id", id)
                .when().get(endpoint);
        return getResponseBody(response);
    }

    public static Response post(String endpoint, String payload) {
        RestAssured.baseURI = EmployeeEndpoint.BASE_URL;
        Response response = RestAssured.
                given().contentType(ContentType.JSON).accept(ContentType.JSON).body(payload)
                .when().post(endpoint);
        return getResponseBody(response);
    }

    public static Response put(String endpoint, String id, String payload) {
        RestAssured.baseURI = EmployeeEndpoint.BASE_URL;
        Response response = RestAssured
                .given().contentType(ContentType.JSON).accept(ContentType.JSON).body(payload)
                .and().pathParam("id", id)
                .when().put(endpoint);
        return getResponseBody(response);
    }

    public static Response delete(String endpoint, String id) {
        RestAssured.baseURI = EmployeeEndpoint.BASE_URL;
        Response response = RestAssured
                .given().pathParam("id", id)
                .when().delete(endpoint);
        return getResponseBody(response);
    }

    private static Response getResponseBody(Response response) {
        response.then().log().body();
        return response;
    }
}
