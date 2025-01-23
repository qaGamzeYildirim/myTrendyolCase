package wiremock.setup;

import com.github.tomakehurst.wiremock.WireMockServer;
import com.github.tomakehurst.wiremock.client.WireMock;
import com.github.tomakehurst.wiremock.core.WireMockConfiguration;
import enums.OperationsTypes;
import org.junit.jupiter.api.BeforeAll;
import wiremock.transformer.ApiResponseTransformer;

import static com.github.tomakehurst.wiremock.client.WireMock.*;

public class WireMockSetup {
    private static WireMockServer wireMockServer;
    private static final String API_RESPONSE_TRANSFORMER = "api-response-transformer";

    @BeforeAll
    public static void startWireMockServer() {
        wireMockServer = new WireMockServer(WireMockConfiguration.wireMockConfig()
                .port(8080)
                .extensions(new ApiResponseTransformer()));
        wireMockServer.start();
        WireMock.configureFor("localhost", 8080);

        stubFor(post(urlPathMatching("/(" + OperationsTypes.addition.name() + "|" + OperationsTypes.
                subtraction.name() +
                "|" + OperationsTypes.multiplication.name() + "|" + OperationsTypes.division.name() + ")"))
                .withHeader("Authorization", containing("Bearer"))
                .withQueryParam("params", matching(".*"))
                .willReturn(aResponse()
                        .withTransformers(API_RESPONSE_TRANSFORMER)));
        stubFor(get(urlPathEqualTo(OperationsTypes.sum.name()))
                .withHeader("Authorization", containing("Bearer"))
                .withQueryParam("params", matching("\\d+"))
                .willReturn(aResponse()
                        .withTransformers(API_RESPONSE_TRANSFORMER)));

        stubFor(any(urlMatching(".*"))
                .willReturn(aResponse()
                        .withStatus(400)
                        .withHeader("Content-Type", "application/json")
                        .withBody("{\"error\": \"Bad Request\"}")));
    }

    public static void stopServer() {
        if (wireMockServer != null) {
            wireMockServer.stop();
        }
    }
}