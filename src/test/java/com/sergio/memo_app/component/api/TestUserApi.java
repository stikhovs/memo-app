package com.sergio.memo_app.component.api;

import com.google.gson.Gson;
import com.sergio.memo_app.api.dto.UserApiDto;
import com.sergio.memo_app.component.base.BaseCT;
import com.sergio.memo_app.persistence.dto.UserDto;
import com.sergio.memo_app.persistence.service.UserPersistenceService;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;

import static io.restassured.RestAssured.given;
import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class TestUserApi extends BaseCT {

    @Autowired
    private UserPersistenceService userPersistenceService;

    @LocalServerPort
    private Integer port;

    @BeforeEach
    void setUp() {
        RestAssured.baseURI = "http://localhost:" + port;
    }

    @Test
    void shouldGetUserByUsername() {
        String response = new Gson().toJson(UserApiDto.builder()
                .id(1)
                .username("test user")
                .email("test@test.com")
                .build());

        given()
                .when()
                .get("/api/user?username=test user")
                .then()
                .statusCode(200)
                .body("id", Matchers.is(1))
                .body("username", Matchers.is("test user"))
                .body("email", Matchers.is("test@test.com"));

    }

    @Test
    void shouldCreateUser() {
        String requestBody = new Gson().toJson(UserApiDto.builder()
                .username("api user")
                .email("api@test.com")
                .build());

        given()
                .contentType(ContentType.JSON)
                .when()
                .body(requestBody)
                .post("/api/user/create")
                .then()
                .statusCode(200)
                .body("id", Matchers.is(2))
                .body("username", Matchers.is("api user"))
                .body("email", Matchers.is("api@test.com"));

        UserDto userDto = userPersistenceService.findBy("api user");
        assertThat(userDto).isNotNull();
        assertThat(userDto.id()).isEqualTo(2);
        assertThat(userDto.username()).isEqualTo("api user");
        assertThat(userDto.email()).isEqualTo("api@test.com");
    }
}
