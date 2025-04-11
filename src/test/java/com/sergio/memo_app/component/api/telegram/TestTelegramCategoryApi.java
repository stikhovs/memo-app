package com.sergio.memo_app.component.api.telegram;

import com.google.gson.Gson;
import com.sergio.memo_app.component.base.BaseCT;
import com.sergio.memo_app.persistence.dto.CategoryDto;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.http.Header;
import io.restassured.http.Headers;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.*;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;

import static com.sergio.memo_app.util.ConstantHelper.Category.*;
import static com.sergio.memo_app.util.ConstantHelper.Header.X_INTERNAL_AUTH_KEY;
import static com.sergio.memo_app.util.ConstantHelper.Header.X_INTERNAL_AUTH_VALUE;
import static com.sergio.memo_app.util.ConstantHelper.User.USER_ID_1;
import static io.restassured.RestAssured.given;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class TestTelegramCategoryApi extends BaseCT {

    @LocalServerPort
    private Integer port;

    @BeforeEach
    void setUp() {
        RestAssured.baseURI = "http://localhost:" + port;
    }

    @Test
    @Order(1)
    void shouldGetById() {
        given()
                .when()
                .headers(Headers.headers(new Header(X_INTERNAL_AUTH_KEY, X_INTERNAL_AUTH_VALUE)))
                .get("/telegram/category?categoryId=%s".formatted(CATEGORY_ID_1))
                .then()
                .statusCode(200)
                .body("id", Matchers.equalTo(CATEGORY_ID_1.intValue()))
                .body("title", Matchers.equalTo(CATEGORY_TITLE_1))
                .body("userId", Matchers.equalTo(USER_ID_1));
    }

    @Test
    @Order(2)
    void shouldSave() {
        String requestBody = new Gson().toJson(
                CategoryDto.builder().title(CATEGORY_TITLE_2).build()
        );

        given()
                .when()
                .contentType(ContentType.JSON)
                .headers(Headers.headers(new Header(X_INTERNAL_AUTH_KEY, X_INTERNAL_AUTH_VALUE)))
                .body(requestBody)
                .post("/telegram/category/save?chatId=%s".formatted(456L))
                .then()
                .statusCode(200)
                .body("id", Matchers.equalTo(CATEGORY_ID_2.intValue()))
                .body("title", Matchers.equalTo(CATEGORY_TITLE_2))
                .body("userId", Matchers.equalTo(USER_ID_1));
    }

    @Test
    @Order(3)
    void shouldGetAllByChatId() {
        given()
                .when()
                .headers(Headers.headers(new Header(X_INTERNAL_AUTH_KEY, X_INTERNAL_AUTH_VALUE)))
                .get("/telegram/category/by-chat?chatId=%s".formatted(456L))
                .then()
                .statusCode(200)
                .body(".", Matchers.hasSize(2))
                .body("@id", Matchers.hasSize(2))
                .body("id[0]", Matchers.equalTo(CATEGORY_ID_1.intValue()))
                .body("id[1]", Matchers.equalTo(CATEGORY_ID_2.intValue()))
                .body("title[0]", Matchers.equalTo(CATEGORY_TITLE_1))
                .body("title[1]", Matchers.equalTo(CATEGORY_TITLE_2))
                .body("userId[0]", Matchers.equalTo(USER_ID_1))
                .body("userId[1]", Matchers.equalTo(USER_ID_1));
    }

    @Test
    @Order(4)
    void shouldUpdate() {
        String requestBody = new Gson().toJson(
                CategoryDto.builder().id(CATEGORY_ID_2).title(CATEGORY_TITLE_2.toUpperCase()).build()
        );

        given()
                .when()
                .contentType(ContentType.JSON)
                .headers(Headers.headers(new Header(X_INTERNAL_AUTH_KEY, X_INTERNAL_AUTH_VALUE)))
                .body(requestBody)
                .put("/telegram/category/update")
                .then()
                .statusCode(200)
                .body("id", Matchers.equalTo(CATEGORY_ID_2.intValue()))
                .body("title", Matchers.equalTo(CATEGORY_TITLE_2.toUpperCase()))
                .body("userId", Matchers.equalTo(USER_ID_1));
    }

    @Test
    @Order(5)
    void shouldDelete() {
        given()
                .when()
                .contentType(ContentType.JSON)
                .headers(Headers.headers(new Header(X_INTERNAL_AUTH_KEY, X_INTERNAL_AUTH_VALUE)))
                .delete("/telegram/category/delete?categoryId=%s".formatted(CATEGORY_ID_2))
                .then()
                .statusCode(200);
    }

}
