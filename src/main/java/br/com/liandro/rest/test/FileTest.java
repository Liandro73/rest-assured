package br.com.liandro.rest.test;

import org.junit.Assert;
import org.junit.Test;

import java.io.*;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.*;

public class FileTest {

    @Test
    public void deveObrigarEnvioArquivo() {
        given()
                .log().all()
            .when()
                .post("https://restapi.wcaquino.me/upload")
            .then()
                .log().all()
                .statusCode(404)  //Deveria ser 400
                .body("error", is("Arquivo não enviado"))
        ;
    }

    @Test
    public void deveFazerUploadArquivo() {
        given()
                .log().all()
                .multiPart("arquivo", new File("src/main/resources/users.pdf"))
            .when()
                .post("https://restapi.wcaquino.me/upload")
            .then()
                .log().all()
                .statusCode(200)
                .body("name", is("users.pdf"))
        ;
    }

    @Test
    public void naoDeveFazerUploadArquivoGrande() {
        given()
                .log().all()
                .multiPart("arquivo", new File("src/main/resources/itext-2.1.0-sources.jar"))
            .when()
                .post("https://restapi.wcaquino.me/upload")
            .then()
                .log().all()
                .time(lessThan(10000L))
                .statusCode(413)
        ;
    }

    @Test
    public void deveBaixarArquivo() throws IOException {
        byte[] image = given()
                .log().all()
            .when()
                .get("https://restapi.wcaquino.me/download")
            .then()
//                .log().all()
                .statusCode(200)
                .extract().asByteArray()
        ;
        File imagem = new File("src/main/resources/file.jpg");
        OutputStream os = new FileOutputStream(imagem);
        os.write(image);
        os.close();

        Assert.assertEquals(94878, imagem.length());
    }

}
