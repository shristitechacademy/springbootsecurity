package com.bookapp.service;

import java.util.List;

import com.bookapp.model.Book;

public interface IBookService {

	// crud operation using the inbuilt methods
	
	// ADMIN
	Book addBook(Book book);
	Book getById(int bookId);
	
	// ADMIN or USER
	List<Book> getAll();
	List<Book> getByAuthor(String author);
	List<Book> getByCategory(String category);
	
	

}
