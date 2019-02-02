package com.carydeng.carydengspring;


import org.junit.Test;
import org.junit.runner.RunWith;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.http.ResponseEntity;
import org.springframework.http.HttpStatus;
import static org.assertj.core.api.Assertions.assertThat;


@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
public class GreetingTest {

    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate restTemplate;

    @Test
    public void greetingShouldReturnDefaultMessage() throws Exception {
        assertThat(this.restTemplate.getForObject("http://localhost:{port}/greeting", String.class, port))
                .contains("{\"id\":2,\"content\":\"Hello, World!\"}");
    }

    @Test
    public void greetingShouldReturnMessageWithName() throws Exception {

        String name = "Spring";
        String url = String.format("http://localhost:%s/greeting?name=%s", port, name);

        ResponseEntity<Greeting> responseEntity = this.restTemplate.getForEntity(url, Greeting.class);
        assertThat(responseEntity.getStatusCodeValue()).isEqualTo(HttpStatus.OK);
        Greeting greeting = responseEntity.getBody();
        assertThat(greeting.getContent()).contains("Hello, " + name + "!");
        //assertThat(this.restTemplate.getForObject("http://localhost:" + port + "/greeting?name=" + name, String.class))
        //        .contains("{\"id\":2,\"content\":\"Hello, " + name + "!\"}");
    }
}