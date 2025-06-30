package com.bookmakase.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ReviewController {
	@GetMapping("/reviews")
	public String reviews() {
		return "This is reviews";
	}
}
