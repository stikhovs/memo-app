package com.sergio.memo_app.component.api;

import com.google.gson.Gson;
import com.sergio.memo_app.component.base.BaseCT;
import com.sergio.memo_app.persistence.dto.CardDto;
import com.sergio.memo_app.persistence.service.CardPersistenceService;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;

import java.util.List;

import static com.sergio.memo_app.util.ConstantHelper.CardSet.CARD_SET_ID_1;
import static io.restassured.RestAssured.given;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class TestCardApi extends BaseCT {

    @Autowired
    private CardPersistenceService cardPersistenceService;

    @LocalServerPort
    private Integer port;

    @BeforeEach
    void setUp() {
        RestAssured.baseURI = "http://localhost:" + port;
    }


    @Test
    @Order(1)
    void shouldCreateCards() {
        String requestBody = new Gson().toJson(List.of(
                CardDto.builder().frontSide("api front 1").backSide("api back 1").build(),
                CardDto.builder().frontSide("api front 2").backSide("api back 2").build()
        ));

        given()
                .contentType(ContentType.JSON)
                .when()
                .body(requestBody)
                .post("/api/card?setId=%s".formatted(CARD_SET_ID_1))
                .then()
                .statusCode(200)
                .body(".", Matchers.hasSize(7))
                .body("@id", Matchers.hasSize(7))
                .body("id[5]", Matchers.equalTo(6))
                .body("id[6]", Matchers.equalTo(7))
                .body("@frontSide", Matchers.hasSize(7))
                .body("frontSide[5]", Matchers.equalTo("api front 1"))
                .body("frontSide[6]", Matchers.equalTo("api front 2"))
                .body("@backSide", Matchers.hasSize(7))
                .body("backSide[5]", Matchers.equalTo("api back 1"))
                .body("backSide[6]", Matchers.equalTo("api back 2"));
    }

    @Test
    @Order(2)
    void shouldFindCards() {
        given()
                .when()
                .get("/api/card?cardSetId=%s".formatted(CARD_SET_ID_1))
                .then()
                .statusCode(200)
                .body(".", Matchers.hasSize(7))
                .body("@id", Matchers.hasSize(7))
                .body("@frontSide", Matchers.hasSize(7))
                .body("@backSide", Matchers.hasSize(7));
    }

    @Test
    @Order(3)
    void shouldUpdateCard() {
        String requestBody = new Gson().toJson(
                CardDto.builder()
                        .id(7L)
                        .frontSide("updated front")
                        .backSide("updated back")
                        .build());

        given()
                .contentType(ContentType.JSON)
                .when()
                .body(requestBody)
                .put("/api/card?cardId=%s".formatted(7)) // TODO: cardId request param can be removed
                .then()
                .statusCode(200)
                .body("id", Matchers.equalTo(7))
                .body("frontSide", Matchers.equalTo("updated front"))
                .body("backSide", Matchers.equalTo("updated back"));
    }
}
