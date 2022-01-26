package br.com.liandro.rest.test;

import io.restassured.http.ContentType;
import org.hamcrest.Matchers;
import org.junit.Test;

import static io.restassured.RestAssured.given;

public class EnvioDadosTest {

    @Test
    public void deveEnviarValorViaQuery() {
        given()
                .log().all()
            .when()
                .get("https://restapi.wcaquino.me/v2/users?format=xml")
            .then()
                .log().all()
                .statusCode(200)
                .contentType(ContentType.XML)
        ;
    }

    @Test
    public void deveEnviarValorViaQueryViaParam() {
        given()
                .log().all()
                .queryParam("format", "xml")
            .when()
                .get("https://restapi.wcaquino.me/v2/users")
            .then()
                .log().all()
                .statusCode(200)
                .contentType(ContentType.XML)
                .contentType(Matchers.containsString("utf-8"))
        ;
    }

    @Test
    public void deveEnviarValorViaQueryViaHeader() {
        given()
                .log().all()
                .accept(ContentType.JSON)
            .when()
                .get("https://restapi.wcaquino.me/v2/users")
            .then()
                .log().all()
                .statusCode(200)
                .contentType(ContentType.HTML)
        ;
    }

}
