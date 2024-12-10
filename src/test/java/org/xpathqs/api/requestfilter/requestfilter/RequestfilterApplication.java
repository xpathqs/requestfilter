package org.xpathqs.api.requestfilter.requestfilter;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@SpringBootApplication
public class RequestfilterApplication {

	public static void main(String[] args) {
		SpringApplication.run(RequestfilterApplication.class, args);
	}

}

@RestController
class RequestFilterTestController {

	@GetMapping("test1")
	String test1() {
		return "hellow1";
	}

	@GetMapping("test2")
	String test2() {
		return "hellow2";
	}
}