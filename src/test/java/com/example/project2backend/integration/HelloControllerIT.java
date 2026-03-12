package com.example.project2backend.integration;

import io.restassured.RestAssured;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.test.context.ActiveProfiles;

import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles("test")
class HelloControllerIT {

    @LocalServerPort
    private int port;

    @BeforeEach
    void setup() {
        RestAssured.port = port;
    }

    @Test
    void helloEndpointReturnsAuthenticatedMessage() {
        given()
                .auth().oauth2("mock-token")
                .when()
                .get("/api/hello")
                .then()
                .statusCode(200)
                .body("message", equalTo("Authenticated ✅"));
    }

    @Test
    void helloEndpointWithoutAuthShouldReturnUnauthorized() {
        when()
                .get("/api/hello")
                .then()
                .statusCode(401);
    }

    @Test
    void testEndpointReturns200() {
        when()
                .get("/api/test")
                .then()
                .statusCode(200);
    }
}