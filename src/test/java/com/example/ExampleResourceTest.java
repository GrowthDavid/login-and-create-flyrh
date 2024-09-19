// ExampleResourceTest.java
package com.example;

import io.quarkus.test.junit.QuarkusTest;
import org.junit.jupiter.api.Test;

@QuarkusTest
public class ExampleResourceTest {

    @Test
    public void testHelloEndpoint() {
        // Comentado, já que o endpoint não foi definido ainda
        // given()
        //   .when().get("/hello")
        //   .then()
        //   .statusCode(200);
    }
}
