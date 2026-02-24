package id.ac.ui.cs.advprog.eshop;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertTrue;

import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class EshopApplicationTests {

    @Test
    void contextLoads() {
        assertTrue(true, "The context should load without issues.");
    }

    @Test
    void testMain() {
        EshopApplication.main(new String[]{});
    }
}