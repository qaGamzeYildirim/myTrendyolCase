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


public class TestCheckSubtraction extends BaseTest {

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
          3- Post subtraction endpoint
          4- Check the status code is 200
          5- Check the result
      */
    @Test
    void testSubtractionWithTwoParameters() {

        postCalculate(AUTHORIZATION, BEARER, token, "80,45", OperationsTypes.subtraction.getEndpoint(),
                200, 35);

    }

    /*
    Test case is:
        1- Give header with token
        2- Give three double parameter values
        3- Post subtraction endpoint
        4- Check the status code is 200
        5- Check the double result
    */
    @Test
    void testSubtractionWithThreeDoubleParameters() {

        postCalculate(AUTHORIZATION, BEARER, token, "5.3,7.4,90.5", OperationsTypes.subtraction.getEndpoint(),
                200, -92.6);
    }

    /*
   Test case is:
       1- Give header with token
       2- Give four negative parameter values
       3- Post subtraction endpoint
       4- Check the status code is 200
       5- Check the negative result
   */
    @Test
    void testSubtractionWithFourNegativeParameters() {

        postCalculate(AUTHORIZATION, BEARER, token, "-89,-9,-9,-3", OperationsTypes.subtraction.getEndpoint(),
                200, -68);
    }

    /*
     Test case is:
         1- Give header with token
         2- Give five parameter values
         3- Post subtraction endpoint
         4- Check the status code is 200
         5- Check the result
     */
    @Test
    void testSubtractionWithFiveParameters() {

        postCalculate(AUTHORIZATION, BEARER, token, "500,37,10,7,65", OperationsTypes.subtraction.getEndpoint(),
                200, 381);
    }

    /*
     Test case is:
         1- Give header with token
         2- Give more than five parameter values
         3- Post subtraction endpoint
         4- Check the status code is 400
         5- Check the error message
     */
    @Test
    void testSubtractionWithMoreThanFiveParameters() {

        postCalculateWithErrorMessage(AUTHORIZATION, BEARER, token, "8,54,7,7,7,7,7,7,7,7,7",
                OperationsTypes.subtraction.getEndpoint(),
                400, "You can give max 5 or min 2 params!");
    }

     /*
     Test case is:
         1- Give header with token
         2- Give less than two parameter values
         3- Post subtraction endpoint
         4- Check the status code is 400
         5- Check the error message
     */

    @Test
    void testSubtractionWithLessThanTwoParameters() {

        postCalculateWithErrorMessage(AUTHORIZATION, BEARER, token, "4", OperationsTypes.subtraction.getEndpoint(),
                400, "You can give max 5 or min 2 params!");
    }

    /*
     Test case is:
         1- Give header with invalid username
         2- Give two parameter values
         3- Post subtraction endpoint
         4- Check the status code is 400
         5- Check the error message in the service response
     */

    @Test
    void testSubtractionWithInvalidUsername() {
        String invalidTokenWithUsername = "dGVzdDI6dGVzdHBhc3M=";
        postCalculateWithErrorMessage(AUTHORIZATION, BEARER, invalidTokenWithUsername, "4,90",
                OperationsTypes.subtraction.getEndpoint(),
                400, "Bad Request: Missing or invalid authorization header.");
    }

    /*
     Test case is:
         1- Give header with invalid password
         2- Give two parameter values
         3- Post subtraction endpoint
         4- Check the status code is 400
         5- Check the error message in the service response
     */

    @Test
    void testSubtractionWithInvalidPassword() {
        String invalidTokenWithPassword = "dGVzdDpnYW16ZQ==";
        postCalculateWithErrorMessage(AUTHORIZATION, BEARER, invalidTokenWithPassword, "4,45",
                OperationsTypes.subtraction.getEndpoint(),
                400, "Bad Request: Missing or invalid authorization header.");
    }

     /*
     Test case is:
         1- Give header without "Bearer"
         2- Give two parameter values
         3- Post subtraction endpoint
         4- Check the status code is 400
         5- Check the error message in the service response
     */

    @Test
    void testSubtractionWithoutBearer() {

        postCalculateWithErrorMessage(AUTHORIZATION, "", token, "4,10",
                OperationsTypes.subtraction.getEndpoint(),
                400, "Bad Request: Missing or invalid authorization header.");
    }

    /*
     Test case is:
         1- Give header without token
         2- Give two parameter values
         3- Post subtraction endpoint
         4- Check the status code is 400
         5- Check the error message in the service response
     */
    @Test
    void testSubtractionWithoutToken() {

        postCalculateWithErrorMessage(AUTHORIZATION, BEARER, "", "4,9", OperationsTypes.subtraction.getEndpoint(),
                400, "Bad Request: Missing or invalid authorization header.");
    }

    @AfterAll
    public static void tearDown() {
        WireMockSetup.stopServer();
    }

}
