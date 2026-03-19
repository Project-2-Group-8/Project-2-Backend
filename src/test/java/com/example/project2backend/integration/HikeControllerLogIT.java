package com.example.project2backend.integration;

import com.example.project2backend.models.HikeLog;
import com.example.project2backend.repositories.HikeLogRepository;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;

import java.util.List;

import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.hasSize;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class HikeLogControllerIT {

    @LocalServerPort
    private int port;

    @Autowired
    private HikeLogRepository hikeLogRepository;

    @BeforeEach
    void setUp() {
        RestAssured.port = port;
        hikeLogRepository.deleteAll(); // clean slate before each test
    }

    @Test
    void logHikeAndGetAll() {
        HikeLog hike = new HikeLog();
        hike.setActivityType("trail");
        hike.setDistanceMile(5.0);

        // POST a hike
        RestAssured.given()
                .contentType(ContentType.JSON)
                .body(hike)
                .when().post("/api/hike_logs")
                .then().statusCode(200)
                .body("activityType", equalTo("trail"))
                .body("distanceMiles", equalTo(5.0f)); // REST Assured uses float for JSON numbers

        // GET all hikes
        RestAssured.given()
                .when().get("/api/hike_logs")
                .then().statusCode(200)
                .body("", hasSize(1));
    }

    @Test
    void getLeaderboardFiltersByType() {
        HikeLog h1 = new HikeLog(); h1.setActivityType("trail"); h1.setDistanceMile(3.0);
        HikeLog h2 = new HikeLog(); h2.setActivityType("trail"); h2.setDistanceMile(6.0);
        HikeLog h3 = new HikeLog(); h3.setActivityType("bike"); h3.setDistanceMile(10.0);

        hikeLogRepository.saveAll(List.of(h1, h2, h3));

        RestAssured.given()
                .queryParam("type", "trail")
                .when().get("/api/hike_logs/leaderboard")
                .then().statusCode(200)
                .body("", hasSize(2))
                .body("[0].distanceMiles", equalTo(6.0f)) // sorted desc
                .body("[1].distanceMiles", equalTo(3.0f));
    }

    @Test
    void getHikeByIdReturnsHike() {
        HikeLog hike = new HikeLog();
        hike.setActivityType("trail");
        hike.setDistanceMile(2.0);
        HikeLog saved = hikeLogRepository.save(hike);

        RestAssured.given()
                .when().get("/api/hike_logs/{id}", saved.getLogId())
                .then().statusCode(200)
                .body("activityType", equalTo("trail"))
                .body("distanceMiles", equalTo(2.0f));
    }
}