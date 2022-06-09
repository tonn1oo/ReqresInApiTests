package com.reqresin.tests;


import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import static com.reqresin.tests.ReqresEndpoints.*;
import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.is;


public class ApiTests {

    String bodyCreateUser = "{ \"name\": \"morpheus\", \"job\": \"leader\" }";
    String bodyRegisterSuccess = "{ \"email\": \"eve.holt@reqres.in\", \"password\": \"pistol\" }";
    String token = "QpwL5tke4Pnpja7X4";
    String email = "{ \"email\": \"peter@klaven\" }";


    @BeforeAll
    static void TestConfig() {

        RestAssured.baseURI = "https://reqres.in/api/";
    }


    @Test
    void SingleUsers() {

        given()
                .contentType(ContentType.JSON)
                .when()
                .log().all()
                .get(listUsers)
                .then()
                .log().body()
                .statusCode(200)
                .body("data.id", is(2));

    }

    @Test
    void SingleUsersNotFound() {

        given()
                .contentType(ContentType.JSON)
                .when()
                .log().all()
                .get(notFoundUsers)
                .then()
                .log().body()
                .statusCode(404)
                .body(is("{}"));
    }

    @Test
    void CreateUserTest() {
        given()
                .body(bodyCreateUser)
                .contentType(ContentType.JSON)
                .when()
                .log().all()
                .post(createUser)
                .then()
                .log().body()
                .statusCode(201)
                .body("name", is("morpheus"))
                .body("job", is("leader"));
    }

    @Test
    void RegisterSuccessFull() {
        given()
                .body(bodyRegisterSuccess)
                .contentType(ContentType.JSON)
                .when()
                .log().all()
                .post(register)
                .then()
                .log().body()
                .statusCode(200)
                .body("token", is(token));
    }

    @Test
    void DeleteUsers() {
        given()
                .contentType(ContentType.JSON)
                .when()
                .log().all()
                .delete(delete)
                .then()
                .log().body()
                .statusCode(204);
    }

    @Test
    void LoginUnSuccessFul() {
        given()
                .body(email)
                .contentType(ContentType.JSON)
                .when()
                .log().uri()
                .post(login)
                .then()
                .log().body()
                .statusCode(400)
                .body("error", is("Missing password"));
    }

}
