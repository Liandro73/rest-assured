package br.com.liandro.rest.test;

import io.restassured.http.ContentType;
import org.hamcrest.xml.HasXPath;
import org.junit.Test;

import static org.hamcrest.Matchers.*;
import static io.restassured.RestAssured.given;

public class Html {

    @Test
    public void deveFazerBuscasComHtml() {
        given()
                .log().all()
            .when()
                .get("https://restapi.wcaquino.me/v2/users")
            .then()
                .log().all()
                .statusCode(200)
                .contentType(ContentType.HTML)
                .body("html.body.div.table.tbody.tr.size()", is(3))
                .body("html.body.div.table.tbody.tr[1].td[2]", is("25"))
                .appendRootPath("html.body.div.table.tbody")
                .body("tr.find{it.toString().startsWith('2')}.td[1]", is("Maria Joaquina"))
        ;
    }

    @Test
    public void deveFazerBuscasComXpathEmHtml() {
        given()
                .log().all()
            .when()
                .get("https://restapi.wcaquino.me/v2/users?format=clean")
            .then()
                .log().all()
                .statusCode(200)
                .contentType(ContentType.HTML)
                .body(hasXPath("count(//table/tr)", is("4")))
                .body(hasXPath("//td[text() = '2']/../td[2]", is("Maria Joaquina")))
        ;
    }

}
