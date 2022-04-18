package com.sbelan.x5testproject;

import static org.junit.jupiter.api.Assertions.assertNotEquals;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.sbelan.x5testproject.model.dto.AuthenticationRequestDto;
import java.io.IOException;
import java.util.Map;
import java.util.Objects;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.http.ResponseEntity;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.annotation.DirtiesContext.ClassMode;
import org.springframework.test.context.TestPropertySource;

@SpringBootTest(webEnvironment = WebEnvironment.DEFINED_PORT)
@DirtiesContext(classMode = ClassMode.BEFORE_EACH_TEST_METHOD)
@TestPropertySource(locations = {"classpath:application-test.yml"})
class ApplicationTests {

	private static final ObjectMapper mapper =
		(new ObjectMapper()).registerModule(new JavaTimeModule());

	@Autowired
	private TestRestTemplate restTemplate;

	//@Test
	public void save_cluster() {
		var authRequest = read(new ClassPathResource("json/auth_request.json"), AuthenticationRequestDto.class);
		ResponseEntity<Map> authResponse = this.restTemplate
			.postForEntity("/api/v1/auth/login", authRequest, Map.class);

		Map<Object, Object> auth = authResponse.getBody();

		assertNotEquals(Objects.requireNonNull(auth).get("token"), "");
	}

	private static <T> T read(Resource resource, Class<T> tClass) {
		try {
			return mapper.readValue(resource.getInputStream(), tClass);
		} catch (IOException var3) {
			throw new TestResourceException(var3);
		}
	}

	private static class TestResourceException extends RuntimeException {
		public TestResourceException(Throwable cause) {
			super(cause);
		}
	}
}
