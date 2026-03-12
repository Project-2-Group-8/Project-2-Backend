package com.example.project2backend.integration;

import com.example.project2backend.repositories.HikeTimeLogRepository;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.test.context.ActiveProfiles;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.hasSize;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles("test")
class HikeTimeLogControllerIT {

    @LocalServerPort
    private int port;

    @Autowired
    private HikeTimeLogRepository repository;

    @BeforeEach
    void setUp() {
        RestAssured.port = port;
        repository.deleteAll(); // clean database before each test
    }

    @Test
    void createAndListHikeTimeLogs() {
        Map<String, Object> req = new HashMap<>();
        req.put("activityType", "trail");
        req.put("notes", "Beautiful hike");
        req.put("someBooleanField", false); // adjust field name if needed

        // POST /api/hike-times
        given()
                .contentType(ContentType.JSON)
                .body(req)
                .when()
                .post("/api/hike-times")
                .then()
                .statusCode(201)
                .body("activityType", equalTo("trail"))
                .body("notes", equalTo("Beautiful hike"));

        // GET /api/hike-times
        given()
                .when()
                .get("/api/hike-times")
                .then()
                .statusCode(200)
                .body("", hasSize(1));
    }

    @Test
    void replaceHikeTimeLog() {
        // Create directly via POST
        Map<String, Object> createReq = new HashMap<>();
        createReq.put("activityType", "mountain");
        createReq.put("notes", "Old note");
        createReq.put("someBooleanField", false);

        int id = given()
                .contentType(ContentType.JSON)
                .body(createReq)
                .when()
                .post("/api/hike-times")
                .then()
                .statusCode(201)
                .extract()
                .path("id");

        // PUT request
        Map<String, Object> replaceReq = new HashMap<>();
        replaceReq.put("activityType", "mountain");
        replaceReq.put("notes", "Updated note");
        replaceReq.put("startTime", LocalDateTime.now().minusHours(2).toString());
        replaceReq.put("endTime", LocalDateTime.now().toString());

        given()
                .contentType(ContentType.JSON)
                .body(replaceReq)
                .when()
                .put("/api/hike-times/{id}", id)
                .then()
                .statusCode(200)
                .body("notes", equalTo("Updated note"));
    }

    @Test
    void patchHikeTimeLog() {
        // Create via POST
        Map<String, Object> createReq = new HashMap<>();
        createReq.put("activityType", "forest");
        createReq.put("notes", "Initial note");
        createReq.put("someBooleanField", false);

        int id = given()
                .contentType(ContentType.JSON)
                .body(createReq)
                .when()
                .post("/api/hike-times")
                .then()
                .statusCode(201)
                .extract()
                .path("id");

        // PATCH request
        Map<String, Object> patchReq = new HashMap<>();
        patchReq.put("notes", "Patched note");

        given()
                .contentType(ContentType.JSON)
                .body(patchReq)
                .when()
                .patch("/api/hike-times/{id}", id)
                .then()
                .statusCode(200)
                .body("notes", equalTo("Patched note"));
    }

    @Test
    void deleteHikeTimeLog() {
        Map<String, Object> createReq = new HashMap<>();
        createReq.put("activityType", "trail");
        createReq.put("notes", "To be deleted");
        createReq.put("someBooleanField", false);

        int id = given()
                .contentType(ContentType.JSON)
                .body(createReq)
                .when()
                .post("/api/hike-times")
                .then()
                .statusCode(201)
                .extract()
                .path("id");

        // DELETE
        given()
                .when()
                .delete("/api/hike-times/{id}", id)
                .then()
                .statusCode(204);

        // Confirm deletion
        given()
                .when()
                .get("/api/hike-times")
                .then()
                .statusCode(200)
                .body("", hasSize(0));
    }
}