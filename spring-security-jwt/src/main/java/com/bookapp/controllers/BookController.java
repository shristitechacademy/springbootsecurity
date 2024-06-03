package com.bookapp.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
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
@RequestMapping("/book-api/v1")
public class BookController {
	@Autowired
	private IBookService bookService;

		@PostMapping("/admin/books")
		ResponseEntity<Book> addBook(@RequestBody Book book) {
			Book nbook = bookService.addBook(book);
			return ResponseEntity.status(HttpStatus.CREATED).body(nbook);
		}
		@GetMapping("/admin/books/bookId/{bookId}")
		ResponseEntity<Book>  getById(@PathVariable("bookId") int bookId) {
			Book nbook = bookService.getById(bookId);
			return ResponseEntity.ok(nbook);
		}
		@GetMapping("/books")
		ResponseEntity<List<Book>> getAll(){
			List<Book> books = bookService.getAll();
			return ResponseEntity.ok(books);
		}
		@GetMapping("/books/author/{author}")
		ResponseEntity<List<Book>> getByAuthor(@PathVariable("author") String author) {
			List<Book> books = bookService.getByAuthor(author);
			return ResponseEntity.ok(books);
		}
		@GetMapping("/books/category/{category}")
		ResponseEntity<List<Book>> getByCategory(@PathVariable("category")String category) {
			List<Book> books = bookService.getByCategory(category);
			return ResponseEntity.ok(books);
		}

}
