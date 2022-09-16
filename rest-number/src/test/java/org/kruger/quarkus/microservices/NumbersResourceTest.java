package org.kruger.quarkus.microservices;

import io.quarkus.test.junit.QuarkusTest;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.*;

@QuarkusTest
public class NumbersResourceTest {

    @Test
    public void testHelloEndpoint() {
        given()
          .when().get("/api/numbers")
          .then()
             .statusCode(200)
             .body("isbn_13", startsWith("13-"))
                .body("isbn_10", startsWith("10-"))
                .body((hasKey("generationDate")));
    }

}