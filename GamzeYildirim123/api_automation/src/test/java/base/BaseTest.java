package base;

import enums.OperationsTypes;
import io.restassured.response.Response;

import java.util.List;

import static io.restassured.RestAssured.given;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;


public class BaseTest {

    public static void postCalculate(String headerName, String headerValue, String token, String params,
                                     String endpoint, int expectedStatusCode, double result) {

        Response response = given()
                .header(headerName, headerValue + token)
                .queryParam("params", params)
                .log().uri()
                .when()
                .post(endpoint);

        response
                .then()
                .log().body()
                .statusCode(expectedStatusCode);

        double actualResult = response.jsonPath().getDouble("result");
        assertThat(actualResult, equalTo(result));
    }

    public static void postCalculateWithErrorMessage(String headerName, String headerValue, String token, String params,
                                                     String endpoint, int expectedStatusCode, String errorMessage) {

        Response response = given()
                .header(headerName, headerValue + token)
                .queryParam("params", params)
                .log().uri()
                .when()
                .post(endpoint);

        response
                .then()
                .log().body()
                .statusCode(expectedStatusCode);

        String result = response.jsonPath().getString("error");
        assertThat(result, equalTo(errorMessage));
    }

    public static void getSum(String headerName, String headerValue, String token, String param,
                              String endpoint, int expectedStatusCode, double result) {

        Response response = given()
                .header(headerName, headerValue + token)
                .queryParam("params", param)
                .log().uri()
                .when()
                .get(endpoint);

        response
                .then()
                .log().body()
                .statusCode(expectedStatusCode);

        double actualResult = response.jsonPath().getDouble("result");
        assertThat(actualResult, equalTo(result));
    }

    public static void getSumWithErrorMessage(String headerName, String headerValue, String token, String param,
                                              String endpoint, int expectedStatusCode, String errorMessage) {

        Response response = given()
                .header(headerName, headerValue + token)
                .queryParam("params", param)
                .log().uri()
                .when()
                .get(endpoint);

        response
                .then()
                .log().body()
                .statusCode(expectedStatusCode);

        String result = response.jsonPath().getString("error");
        assertThat(result, equalTo(errorMessage));
    }
}
