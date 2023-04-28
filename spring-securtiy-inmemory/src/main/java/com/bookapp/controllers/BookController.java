package com.bookapp.controllers;

import java.util.Arrays;
import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class BookController {

	@GetMapping("/")
	public String showMessage() {
		return "Welcome to online Book Library";
	}
	
	@GetMapping("/user/books")
	public List<String> showBooks(){
		return Arrays.asList("Java in Action","Spring in Action");
	}
	
	@GetMapping("/user/books/category/{category}")
	public List<String> getByCategory(@PathVariable("category")String category){
		if(category.equals("Tech"))
			return Arrays.asList("Java in Action","Spring in Action");
		else if(category.equals("comics"))
			return Arrays.asList("Tinkle","Astrix");
		else
			return Arrays.asList("no books");
	}
	
	@GetMapping("/admin/books/insert")
	public String addBook() {
		return "book added";
	}
	
	@GetMapping("/admin/books/update")
	public String updateBook() {
		return "book updated";
	}
}
