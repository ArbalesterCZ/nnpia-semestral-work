package st43189.upce.cz.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HelloWorldController {

	@RequestMapping({ "/hello" })
	public String hello() {
		return "Hello World";
	}


	@RequestMapping({ "/users" })
	public String user() {
		return "Hello World";
	}


}