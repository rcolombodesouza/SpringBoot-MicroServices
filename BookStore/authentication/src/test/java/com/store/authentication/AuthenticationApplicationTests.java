package com.store.authentication;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@SpringBootTest
class AuthenticationApplicationTests {

	@Test
	void contextLoads() {
	}

	@Test
	public void test() {
		System.out.println(new BCryptPasswordEncoder().encode("root"));
	}

}
