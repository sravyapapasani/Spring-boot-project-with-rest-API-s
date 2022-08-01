package com.springboot.jettyserver.example;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT, classes = ExampleApplication.class)
@AutoConfigureMockMvc
@ActiveProfiles("test")
public class TestFile {
    @Autowired
    private MockMvc mockMvc;

    @Test
    public void testcase() throws Exception {
        Thread.sleep(1000);
        this.mockMvc.perform(post("http://localhost:8080/api/example/firstRequest")).andExpect(status().isOk());
    }
}

