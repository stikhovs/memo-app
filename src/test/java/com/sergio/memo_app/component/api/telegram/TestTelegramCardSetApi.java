package com.sergio.memo_app.component.api.telegram;

import com.google.gson.Gson;
import com.sergio.memo_app.component.base.BaseCT;
import com.sergio.memo_app.persistence.dto.CardDto;
import com.sergio.memo_app.persistence.dto.CardSetDto;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.http.Header;
import io.restassured.http.Headers;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;

import java.util.List;
import java.util.UUID;

import static com.sergio.memo_app.util.ConstantHelper.Header.X_INTERNAL_AUTH_KEY;
import static com.sergio.memo_app.util.ConstantHelper.Header.X_INTERNAL_AUTH_VALUE;
import static io.restassured.RestAssured.given;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class TestTelegramCardSetApi extends BaseCT {

    @LocalServerPort
    private Integer port;

    @BeforeEach
    void setUp() {
        RestAssured.baseURI = "http://localhost:" + port;
    }


    @Test
//    @Order(1)
    void shouldUpdateCardSet() {
        String requestBody = new Gson().toJson(CardSetDto.builder()
                .id(1L)
                .title("title 1")
                .cards(List.of(CardDto.builder().id(1L).frontSide("something").backSide("snth").build()))
                .uuid(UUID.fromString("fa89840d-44ba-455e-91ce-589f3b3a7b24"))
                .categoryId(1L)
                .build());

        given()
                .contentType(ContentType.JSON)
                .when()
                .body(requestBody)
                .headers(Headers.headers(new Header(X_INTERNAL_AUTH_KEY, X_INTERNAL_AUTH_VALUE)))
                .put("/telegram/set/update")
                .then()
                .statusCode(200)
                .body("id", Matchers.is(1))
                .body("title", Matchers.is("title 1"))
                .body("uuid", Matchers.is("fa89840d-44ba-455e-91ce-589f3b3a7b24"))
                .body("categoryId", Matchers.is(1));
    }

}
