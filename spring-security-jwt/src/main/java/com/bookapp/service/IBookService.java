package com.bookapp.service;

import java.util.List;

import com.bookapp.exceptions.BookNotFoundException;
import com.bookapp.model.Book;

public interface IBookService {
	// ADMIN
	Book addBook(Book book);
	Book getById(int bookId) throws BookNotFoundException;

	// ADMIN or USER
	List<Book> getAll();
	List<Book> getByAuthor(String author) throws BookNotFoundException;
	List<Book> getByCategory(String category) throws BookNotFoundException;

}
