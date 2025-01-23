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


public class TestCheckMultiplication extends BaseTest {

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
          3- Post multiplication endpoint
          4- Check the status code is 200
          5- Check the result
      */
    @Test
    void testMultiplicationWithTwoParameters() {

        postCalculate(AUTHORIZATION, BEARER, token, "7,8", OperationsTypes.multiplication.getEndpoint(),
                200, 56);

    }

    /*
    Test case is:
        1- Give header with token
        2- Give three double parameter values
        3- Post multiplication endpoint
        4- Check the status code is 200
        5- Check the double result
    */
    @Test
    void testMultiplicationWithThreeDoubleParameters() {

        postCalculate(AUTHORIZATION, BEARER, token, "5.3,7.4,90.5", OperationsTypes.multiplication.getEndpoint(),
                200, 3549.41);
    }

    /*
   Test case is:
       1- Give header with token
       2- Give four negative parameter values
       3- Post multiplication endpoint
       4- Check the status code is 200
       5- Check the negative result
   */
    @Test
    void testMultiplicationWithFourNegativeParameters() {

        postCalculate(AUTHORIZATION, BEARER, token, "-89,-9,-9,-3", OperationsTypes.multiplication.getEndpoint(),
                200, 21627);
    }

    /*
     Test case is:
         1- Give header with token
         2- Give five parameter values
         3- Post multiplication endpoint
         4- Check the status code is 200
         5- Check the result
     */
    @Test
    void testMultiplicationWithFiveParameters() {

        postCalculate(AUTHORIZATION, BEARER, token, "43,37,10,7,65", OperationsTypes.multiplication.getEndpoint(),
                200, 7239050);
    }

    /*
     Test case is:
         1- Give header with token
         2- Give more than five parameter values
         3- Post multiplication endpoint
         4- Check the status code is 400
         5- Check the error message
     */
    @Test
    void testMultiplicationWithMoreThanFiveParameters() {

        postCalculateWithErrorMessage(AUTHORIZATION, BEARER, token, "8,54,7,7,7,7,7,7,7,7,7",
                OperationsTypes.multiplication.getEndpoint(),
                400, "You can give max 5 or min 2 params!");
    }

     /*
     Test case is:
         1- Give header with token
         2- Give less than two parameter values
         3- Post multiplication endpoint
         4- Check the status code is 400
         5- Check the error message
     */

    @Test
    void testMultiplicationWithLessThanTwoParameters() {

        postCalculateWithErrorMessage(AUTHORIZATION, BEARER, token, "4", OperationsTypes.multiplication.getEndpoint(),
                400, "You can give max 5 or min 2 params!");
    }

    /*
     Test case is:
         1- Give header with invalid username
         2- Give two parameter values
         3- Post multiplication endpoint
         4- Check the status code is 400
         5- Check the error message in the service response
     */

    @Test
    void testMultiplicationWithInvalidUsername() {
        String invalidTokenWithUsername = "dGVzdDI6dGVzdHBhc3M=";
        postCalculateWithErrorMessage(AUTHORIZATION, BEARER, invalidTokenWithUsername, "4,90",
                OperationsTypes.multiplication.getEndpoint(),
                400, "Bad Request: Missing or invalid authorization header.");
    }

    /*
     Test case is:
         1- Give header with invalid password
         2- Give two parameter values
         3- Post multiplication endpoint
         4- Check the status code is 400
         5- Check the error message in the service response
     */

    @Test
    void testMultiplicationWithInvalidPassword() {
        String invalidTokenWithPassword = "dGVzdDpnYW16ZQ==";
        postCalculateWithErrorMessage(AUTHORIZATION, BEARER, invalidTokenWithPassword, "4,45",
                OperationsTypes.multiplication.getEndpoint(),
                400, "Bad Request: Missing or invalid authorization header.");
    }

     /*
     Test case is:
         1- Give header without "Bearer"
         2- Give two parameter values
         3- Post multiplication endpoint
         4- Check the status code is 400
         5- Check the error message in the service response
     */

    @Test
    void testMultiplicationWithoutBearer() {

        postCalculateWithErrorMessage(AUTHORIZATION, "", token, "4,10",
                OperationsTypes.multiplication.getEndpoint(),
                400, "Bad Request: Missing or invalid authorization header.");
    }

    /*
     Test case is:
         1- Give header without token
         2- Give two parameter values
         3- Post multiplication endpoint
         4- Check the status code is 400
         5- Check the error message in the service response
     */
    @Test
    void testMultiplicationWithoutToken() {

        postCalculateWithErrorMessage(AUTHORIZATION, BEARER, "", "4,9", OperationsTypes.multiplication.getEndpoint(),
                400, "Bad Request: Missing or invalid authorization header.");
    }

    /*
      Test case is:
          1- Give header with token
          2- Give two parameter values with zero
          3- Post multiplication endpoint
          4- Check the status code is 200
          5- Check the result
      */
    @Test
    void testMultiplicationWithZero() {

        postCalculate(AUTHORIZATION, BEARER, token, "10899,0", OperationsTypes.multiplication.getEndpoint(),
                200, 0);

    }

    /*
     Test case is:
         1- Give header with token
         2- Give two parameter values with one
         3- Post multiplication endpoint
         4- Check the status code is 200
         5- Check the result
     */
    @Test
    void testMultiplicationWithOne() {

        postCalculate(AUTHORIZATION, BEARER, token, "90,1", OperationsTypes.multiplication.getEndpoint(),
                200, 90);

    }


    @AfterAll
    public static void tearDown() {
        WireMockSetup.stopServer();
    }

}
