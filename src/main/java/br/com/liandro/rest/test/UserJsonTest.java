package br.com.liandro.rest.test;

import io.restassured.RestAssured;
import io.restassured.http.Method;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import org.junit.Assert;
import org.junit.Test;

import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.*;

public class UserJsonTest {

    private final String url = "http://restapi.wcaquino.me/users/";

    @Test
    public void deveVerificarPrimeiroNivel() {
        given()
            .when()
                .get(url + "1")
            .then()
                .statusCode(200)
                .body("id", is(1))
                .body("name", containsString("Silva"))
                .body("age", greaterThan(18));
    }

    @Test
    public void deveVerificarNivelOutrasFormas() {
        Response response = RestAssured.request(Method.GET, url + "1");

        //path
        Assert.assertEquals(new Integer(1), response.path("id"));
        Assert.assertEquals(new Integer(1), response.path("%s","id"));

        //jsonpath
        JsonPath jsonPath = new JsonPath(response.asString());
        Assert.assertEquals(1, jsonPath.getInt("id"));

        //from
        int id = JsonPath.from(response.asString()).getInt("id");
        Assert.assertEquals(1, id);

    }

    @Test
    public void deveVerificarSegundoNivel() {
        given()
            .when()
                .get(url + "2")
            .then()
                .statusCode(200)
                .body("name", containsString("Joaquina"))
                .body("endereco.rua", is("Rua dos bobos"));
    }

}