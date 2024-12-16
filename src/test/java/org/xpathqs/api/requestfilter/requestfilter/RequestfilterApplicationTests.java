package org.xpathqs.api.requestfilter.requestfilter;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.HttpEntity;
import org.xpathqs.api.requestfilter.requestfilter.dto.AddDelayRequest;
import org.xpathqs.api.requestfilter.requestfilter.dto.ConstantDelayBehaviour;
import org.xpathqs.api.requestfilter.requestfilter.dto.RequestUrlPattern;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.springframework.test.web.servlet.result.StatusResultMatchersExtensionsKt.isEqualTo;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class RequestfilterApplicationTests {

	@Autowired
	ObjectMapper mapper;

	@LocalServerPort
	int port;

	@Autowired
	TestRestTemplate restTemplate;

	String getUrl() {
		return "http://localhost:" + port;
	}

	@Test
	void contextLoads() {
		assertThat(
				restTemplate.getForObject(getUrl() + "/test1", String.class)
		).isEqualTo("hellow1");
	}

	@Test
	void addDelay() throws JsonProcessingException {
		mapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);

		RequestUrlPattern requestPattern = new RequestUrlPattern();
		requestPattern.equals = "test1";

		ConstantDelayBehaviour delay = new ConstantDelayBehaviour(100L);
		String json = mapper.writeValueAsString(
				new AddDelayRequest(
					requestPattern,
					null,
					null,
					delay
				)
		);

		System.out.println(json);

		AddDelayRequest obj = mapper.readValue(json, AddDelayRequest.class);

		System.out.println(obj.delay.type);

		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		HttpEntity request = new HttpEntity(json, headers);

		restTemplate.postForEntity(
				    getUrl() + "/delay/add", request, String.class
		);

		System.out.println(
				restTemplate.getForObject(getUrl() + "/delay/dump", String.class)
		);

		assertThat(
				restTemplate.getForObject(getUrl() + "/test1", String.class)
		).isEqualTo("{}");

		assertThat(
				restTemplate.getForObject(getUrl() + "/test2", String.class)
		).isEqualTo("hellow2");
	}

}
