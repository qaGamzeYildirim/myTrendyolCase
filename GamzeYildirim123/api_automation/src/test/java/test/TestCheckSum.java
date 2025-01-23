package test;

import base.BaseTest;
import enums.OperationsTypes;
import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import wiremock.setup.WireMockSetup;

import java.util.Base64;


public class TestCheckSum extends BaseTest {

    private static final String USER_NAME = "test";
    private static final String PASSWORD = "testpass";
    private static String token;
    private static final String AUTHORIZATION = "Authorization";
    private static final String BEARER = "Bearer ";
    private static final String BASIC = "Basic ";


    @BeforeAll
    public static void setUp() {
        WireMockSetup.startWireMockServer();
        RestAssured.baseURI = "http://localhost:8080";

        String credentials = Base64.getEncoder().encodeToString((USER_NAME + ":" + PASSWORD).getBytes());
        Response loginResponse = (Response) RestAssured.given().header(AUTHORIZATION, BASIC + credentials)
                .post(OperationsTypes.user.getEndpoint()).then().log().body().extract();

        JsonPath loginJsonPath = new JsonPath(loginResponse.asString());

        token = loginJsonPath.get("token");

    }

    /*
      Test case is:
          1- Give header with token
          2- Give n
          3- Get Sum endpoint
          4- Check the status code is 200
          5- Check the result
      */
    @Test
    void testSum() {
        getSum(AUTHORIZATION, BEARER, token, "5", OperationsTypes.sum.getEndpoint(), 200, 15);
    }

    /*
     Test case is:
         1- Give header with token
         2- Give two parameters
         3- Get Sum endpoint
         4- Check the status code is 400
         5- Check the error message
     */
    @Test
    void testSumWithTwoParameters() {
        getSumWithErrorMessage(AUTHORIZATION, BEARER, token, "5,9", OperationsTypes.sum.getEndpoint(),
                400, "Bad Request: Only one parameter is allowed for the sum endpoint.");
    }

    /*
     Test case is:
         1- Give header with invalid username
         2- Give n
         3- Get Sum endpoint
         4- Check the status code is 400
         5- Check the error message in the service response
     */

    @Test
    void testSumWithInvalidUsername() {
        String invalidTokenWithUsername = "dGVzdDI6dGVzdHBhc3M=";
        getSumWithErrorMessage(AUTHORIZATION, BEARER, invalidTokenWithUsername, "9", OperationsTypes
                .sum.getEndpoint(), 400, "Bad Request: Missing or invalid authorization header.");
    }

    /*
     Test case is:
         1- Give header with invalid password
         2- Give n
         3- Get Sum endpoint
         4- Check the status code is 400
         5- Check the error message in the service response
     */

    @Test
    void testSumWithInvalidPassword() {
        String invalidTokenWithPassword = "dGVzdDpnYW16ZQ==";
        getSumWithErrorMessage(AUTHORIZATION, BEARER, invalidTokenWithPassword, "45", OperationsTypes
                .sum.getEndpoint(), 400, "Bad Request: Missing or invalid authorization header.");
    }

     /*
     Test case is:
         1- Give header without "Bearer"
         2- Give n
         3- Get Sum endpoint
         4- Check the status code is 400
         5- Check the error message in the service response
     */

    @Test
    void testSumWithoutBearer() {

        getSumWithErrorMessage(AUTHORIZATION, "", token, "4", OperationsTypes.sum.getEndpoint()
                , 400, "Bad Request: Missing or invalid authorization header.");
    }

    /*
     Test case is:
         1- Give header without token
         2- Give n
         3- Get Sum endpoint
         4- Check the status code is 400
         5- Check the error message in the service response
     */
    @Test
    void testSumWithoutToken() {

        getSumWithErrorMessage(AUTHORIZATION, BEARER, "", "4", OperationsTypes.sum.getEndpoint(),
                400, "Bad Request: Missing or invalid authorization header.");
    }


    @AfterAll
    public static void tearDown() {
        WireMockSetup.stopServer();
    }

}
