package wiremock.transformer;

import com.github.tomakehurst.wiremock.extension.ResponseTransformerV2;
import com.github.tomakehurst.wiremock.http.HttpHeader;
import com.github.tomakehurst.wiremock.http.HttpHeaders;
import com.github.tomakehurst.wiremock.http.Response;
import com.github.tomakehurst.wiremock.stubbing.ServeEvent;
import enums.OperationsTypes;
import org.json.JSONObject;

import java.util.Arrays;
import java.util.Base64;
import java.util.HashMap;
import java.util.Map;


public class ApiResponseTransformer implements ResponseTransformerV2 {
    private static final String USERNAME = "test";
    private static final String PASSWORD = "testpass";
    private static final Map<String, String> tokenStore = new HashMap<>();

    @Override
    public String getName() {
        return "api-response-transformer";
    }

    @Override
    public Response transform(Response response, ServeEvent serveEvent) {

        String requestUrl = serveEvent.getRequest().getUrl();
        String authorization = serveEvent.getRequest().getHeader("Authorization");

        if (requestUrl.equals(OperationsTypes.user.getEndpoint())) {
            String credentials = authorization != null ? authorization.substring(6) : null;
            if (credentials == null) {
                return Response.Builder.like(response)
                        .but().status(401)
                        .body("{\"error\": \"Unauthorized. Missing Authorization header.\"}")
                        .build();
            }

            String decodedCredentials = new String(Base64.getDecoder().decode(credentials));
            String[] userPass = decodedCredentials.split(":");

            if (userPass.length != 2 || !USERNAME.equals(userPass[0]) || !PASSWORD.equals(userPass[1])) {
                return Response.Builder.like(response)
                        .but().status(401)
                        .body("{\"error\": \"Unauthorized. Invalid username or password.\"}")
                        .build();
            }

            String token = Base64.getEncoder().encodeToString((USERNAME + ":" + PASSWORD).getBytes());
            tokenStore.put(token, USERNAME);

            return Response.Builder.like(response)
                    .but().status(200)
                    .headers(new HttpHeaders(new HttpHeader("Content-Type", "application/json")))
                    .body("{\"message\": \"Login successful\", \"token\": \"" + token + "\"}")
                    .build();
        }

        if (authorization == null || !authorization.startsWith("Bearer ") || !tokenStore.containsKey(authorization
                .split(" ")[1])) {
            return Response.Builder.like(response)
                    .but().status(400)
                    .body("{\"error\": \"Bad Request: Missing or invalid authorization header.\"}")
                    .build();
        }
        JSONObject responseBody = new JSONObject();
        String params = serveEvent.getRequest().queryParameter("params").firstValue();
        String[] paramValues = params.split(",");
        if ((paramValues.length > 5 || paramValues.length < 2) && !requestUrl.contains(OperationsTypes.sum.getEndpoint())) {
            return Response.Builder.like(response)
                    .but().status(400)
                    .body("{\"error\": \"You can give max 5 or min 2 params!\"}")
                    .build();
        }
        double result = 0;
        if (requestUrl.contains(OperationsTypes.addition.getEndpoint())) {
            result = Arrays.stream(paramValues).mapToDouble(Double::parseDouble).sum();
        } else if (requestUrl.contains(OperationsTypes.subtraction.getEndpoint())) {
            result = Arrays.stream(paramValues).mapToDouble(Double::parseDouble)
                    .reduce((a, b) -> a - b).orElse(0);
        } else if (requestUrl.contains(OperationsTypes.multiplication.getEndpoint())) {
            result = Arrays.stream(paramValues).mapToDouble(Double::parseDouble)
                    .reduce((a, b) -> a * b).orElse(0);
        } else if (requestUrl.contains(OperationsTypes.division.getEndpoint())) {

            for (int i = 1; i < paramValues.length; i++) {
                if (Double.parseDouble(paramValues[i]) == 0) {
                    return Response.Builder.like(response)
                            .but().status(405)
                            .body("{\"error\": \"Method Not Allowed: Division by zero is not allowed.\"}")
                            .build();
                }
            }
            result = Arrays.stream(paramValues).mapToDouble(Double::parseDouble)
                    .reduce((a, b) -> a / b).orElse(0);
        } else if (requestUrl.contains(OperationsTypes.sum.getEndpoint())) {
            if (paramValues.length > 1) {
                return Response.Builder.like(response)
                        .but().status(400)
                        .body("{\"error\": \"Bad Request: Only one parameter is allowed for the sum endpoint.\"}")
                        .build();
            }
            int n = Integer.parseInt(paramValues[0]);
            result = (n * (n + 1)) / 2.0;
        } else {
            return Response.Builder.like(response)
                    .but().status(404)
                    .body("{\"error\": \"Endpoint not found.\"}")
                    .build();
        }
        String userName = tokenStore.get(authorization.split(" ")[1]);
        responseBody.put("result", result);
        responseBody.put("user", userName);
        return Response.Builder.like(response)
                .but().status(200)
                .body(responseBody.toString())
                .build();
    }
}

