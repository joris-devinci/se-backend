package com.example.demo;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

// Tests get a clean, temporary database and never modify local accounts.
@SpringBootTest(properties = {
    "spring.datasource.url=jdbc:h2:mem:context_test;DB_CLOSE_DELAY=-1",
    "spring.jpa.hibernate.ddl-auto=create-drop"
})
class DemoApplicationTests {

	@Test
	void contextLoads() {
	}

}
