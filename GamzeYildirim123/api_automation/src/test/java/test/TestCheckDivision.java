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


public class TestCheckDivision extends BaseTest {

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
        Response loginResponse = (Response) RestAssured.given()
                .header(AUTHORIZATION, BASIC + credentials)
                .post(OperationsTypes.user.getEndpoint())
                .then().log().body().extract();

        JsonPath loginJsonPath = new JsonPath(loginResponse.asString());

        token = loginJsonPath.get("token");

    }

    /*
      Test case is:
          1- Give header with token
          2- Give two parameter values
          3- Post division endpoint
          4- Check the status code is 200
          5- Check the result
      */
    @Test
    void testDivisionWithTwoParameters() {

        postCalculate(AUTHORIZATION, BEARER, token, "70,6", OperationsTypes.division.getEndpoint(),
                200, 11.666667);

    }

    /*
    Test case is:
        1- Give header with token
        2- Give three double parameter values
        3- Post division endpoint
        4- Check the status code is 200
        5- Check the double result
    */
    @Test
    void testDivisionWithThreeDoubleParameters() {

        postCalculate(AUTHORIZATION, BEARER, token, "36.6,3.2,2.5", OperationsTypes.division.getEndpoint(),
                200, 4.575);
    }

    /*
   Test case is:
       1- Give header with token
       2- Give four negative parameter values
       3- Post division endpoint
       4- Check the status code is 200
       5- Check the result
   */
    @Test
    void testDivisionWithFourNegativeParameters() {

        postCalculate(AUTHORIZATION, BEARER, token, "-600,-12,-3,-6", OperationsTypes.division.getEndpoint(),
                200, 2.7777777);
    }

    /*
     Test case is:
         1- Give header with token
         2- Give five parameter values
         3- Post division endpoint
         4- Check the status code is 200
         5- Check the result
     */
    @Test
    void testDivisionWithFiveParameters() {

        postCalculate(AUTHORIZATION, BEARER, token, "500,10,4,5,2", OperationsTypes.division.getEndpoint(),
                200, 1.25);
    }

    /*
     Test case is:
         1- Give header with token
         2- Give more than five parameter values
         3- Post division endpoint
         4- Check the status code is 400
         5- Check the error message
     */
    @Test
    void testDivisionWithMoreThanFiveParameters() {

        postCalculateWithErrorMessage(AUTHORIZATION, BEARER, token, "8,54,7,7,7,7,7,7,7,7,7",
                OperationsTypes.division.getEndpoint(),
                400, "You can give max 5 or min 2 params!");
    }

     /*
     Test case is:
         1- Give header with token
         2- Give less than two parameter values
         3- Post division endpoint
         4- Check the status code is 400
         5- Check the error message
     */

    @Test
    void testDivisionWithLessThanTwoParameters() {

        postCalculateWithErrorMessage(AUTHORIZATION, BEARER, token, "4", OperationsTypes.division.getEndpoint(),
                400, "You can give max 5 or min 2 params!");
    }

    /*
     Test case is:
         1- Give header with invalid username
         2- Give two parameter values
         3- Post division endpoint
         4- Check the status code is 400
         5- Check the error message in the service response
     */

    @Test
    void testDivisionWithInvalidUsername() {
        String invalidTokenWithUsername = "dGVzdDI6dGVzdHBhc3M=";
        postCalculateWithErrorMessage(AUTHORIZATION, BEARER, invalidTokenWithUsername,
                "4,90", OperationsTypes.division.getEndpoint(),
                400, "Bad Request: Missing or invalid authorization header.");
    }

    /*
     Test case is:
         1- Give header with invalid password
         2- Give two parameter values
         3- Post division endpoint
         4- Check the status code is 400
         5- Check the error message in the service response
     */

    @Test
    void testDivisionWithInvalidPassword() {
        String invalidTokenWithPassword = "dGVzdDpnYW16ZQ==";
        postCalculateWithErrorMessage(AUTHORIZATION, BEARER, invalidTokenWithPassword,
                "4,45", OperationsTypes.division.getEndpoint(),
                400, "Bad Request: Missing or invalid authorization header.");
    }

     /*
     Test case is:
         1- Give header without "Bearer"
         2- Give two parameter values
         3- Post division endpoint
         4- Check the status code is 400
         5- Check the error message in the service response
     */

    @Test
    void testDivisionWithoutBearer() {

        postCalculateWithErrorMessage(AUTHORIZATION, "", token, "4,10", OperationsTypes.division.getEndpoint(),
                400, "Bad Request: Missing or invalid authorization header.");
    }

    /*
     Test case is:
         1- Give header without token
         2- Give two parameter values
         3- Post division endpoint
         4- Check the status code is 400
         5- Check the error message in the service response
     */
    @Test
    void testDivisionWithoutToken() {

        postCalculateWithErrorMessage(AUTHORIZATION, BEARER, "", "4,9", OperationsTypes.division.getEndpoint(),
                400, "Bad Request: Missing or invalid authorization header.");
    }

    /*
      Test case is:
          1- Give header with token
          2- Give two parameter values (first parameter should be 0)
          3- Post division endpoint
          4- Check the status code is 200
          5- Check the result
      */
    @Test
    void testDivisionFirstParameterWithZero() {

        postCalculate(AUTHORIZATION, BEARER, token, "0,6", OperationsTypes.division.getEndpoint(),
                200, 0);

    }

    /*
      Test case is:
          1- Give header with token
          2- Give two parameter values (second parameter should be 0)
          3- Post division endpoint
          4- Check the status code is 200
          5- Check the result
      */
    @Test
    void testDivisionSecondParameterWithZero() {

        postCalculateWithErrorMessage(AUTHORIZATION, BEARER, token, "9,0", OperationsTypes.division.getEndpoint(),
                405, "Method Not Allowed: Division by zero is not allowed.");
    }



    @AfterAll
    public static void tearDown() {
        WireMockSetup.stopServer();
    }

}
