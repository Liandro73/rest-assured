package br.com.liandro.rest.test;

import io.restassured.RestAssured;
import io.restassured.http.Method;
import io.restassured.response.Response;
import io.restassured.response.ValidatableResponse;
import org.junit.Test;

import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.*;
import static org.junit.Assert.*;

public class OlaMundoTest {

    private final String url = "http://restapi.wcaquino.me/ola";

    @Test
    public void testOlaMundo() {
        Response response = request(Method.GET, url);
        assertEquals("Ola Mundo!", response.getBody().asString());
        assertEquals("O status code devera ser 200", 200, response.statusCode());

        ValidatableResponse validacao = response.then();
        validacao.statusCode(200);
    }

    @Test
    public void padroesRestAssured() {
        //Padrão 1 - Verbose
        Response response = request(Method.GET, url);
        ValidatableResponse validacao = response.then();
        validacao.statusCode(200);

        //Padrão 2 - Clean
        RestAssured.get(url).then().statusCode(200);

        //Padrão 3 - BDD
        given()
            .when()
                .get(url)
            .then()
                .assertThat().statusCode(200);
    }

    @Test
    public void matchersHamcrest() {
        assertThat("Maria", is("Maria"));
        assertThat("Maria", anyOf(is("Maria"), is("Joaquina")));
        assertThat("Joaquina", allOf(startsWith("Joa"), endsWith("ina"), containsString("qu")));
    }

    @Test
    public void validarBody() {
        given()
            .when()
                .get(url)
            .then()
                .assertThat().statusCode(200)
                .body(is("Ola Mundo!"));
    }

}
