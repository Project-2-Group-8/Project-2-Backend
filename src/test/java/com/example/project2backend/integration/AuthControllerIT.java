package com.example.project2backend.integration;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;

import static org.hamcrest.Matchers.equalTo;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class AuthControllerIT {

    @LocalServerPort
    private int port;

    @BeforeEach
    void setUp() {
        RestAssured.port = port;
    }

    @Test
    void loginRedirectsToGoogle() {
        RestAssured.given()
                .when().get("/auth/login")
                .then()
                .statusCode(302)
                .header("Location", "/oauth2/authorization/google");
    }

    @Test
    void signupRedirectsToGoogle() {
        RestAssured.given()
                .when().get("/auth/signup")
                .then()
                .statusCode(302)
                .header("Location", "/oauth2/authorization/google");
    }

    @Test
    void meWithoutAuthReturnsUnauthenticated() {
        RestAssured.given()
                .when().get("/auth/me")
                .then()
                .statusCode(200)
                .contentType(ContentType.JSON)
                .body("authenticated", equalTo(false));
    }
}