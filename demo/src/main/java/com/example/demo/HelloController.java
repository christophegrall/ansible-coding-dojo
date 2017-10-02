package com.example.demo;

import org.springframework.web.bind.annotation.RestController;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.RequestMapping;

@RestController
public class HelloController {

	@Value("${server_name:default-server}")
	private String serverName;

	@RequestMapping("/")
	public String index() {
		return "Welcome from " + serverName;
	}

}
