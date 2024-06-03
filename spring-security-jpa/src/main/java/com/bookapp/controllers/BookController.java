package com.bookapp.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.bookapp.model.Book;
import com.bookapp.service.IBookService;

@RestController
@RequestMapping("/book-api")
public class BookController {
	private IBookService bookService;
	
	@Autowired
	public void setBookService(IBookService bookService) {
		this.bookService = bookService;
	}
	// ADMIN
	@PostMapping("/admin/books")
	ResponseEntity<Book> addBook(@RequestBody Book book) {
		Book nbook = bookService.addBook(book);
		return ResponseEntity.accepted().body(nbook);
		
	}
	@GetMapping("/admin/books/id/{bookId}")
	ResponseEntity<Book> getById(@PathVariable("bookId") int bookId) {
		Book book = bookService.getById(bookId);
		return ResponseEntity.ok(book);
	}
	
	@GetMapping("/user/books")
	ResponseEntity<List<Book>> getAll(){
		List<Book> books = bookService.getAll();
		return ResponseEntity.ok(books);
	}
	@GetMapping("/user/books/author/{author}")
	ResponseEntity<List<Book>> getByAuthor(@PathVariable("author") String author){
		List<Book> books = bookService.getByAuthor(author);
		return ResponseEntity.ok(books);
	}
	@GetMapping("/user/books/category/{category}")
	ResponseEntity<List<Book>> getByCategory(@PathVariable("category") String category){
		List<Book> books = bookService.getByAuthor(category);
		return ResponseEntity.ok(books);
	}
	
}
