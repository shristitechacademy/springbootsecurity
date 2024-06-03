package com.bookapp.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bookapp.exceptions.BookNotFoundException;
import com.bookapp.model.Book;
import com.bookapp.repository.IBookRepository;

@Service
public class BookServiceImpl implements IBookService{

	private IBookRepository bookRepository;
	@Autowired
	public void setBookRepository(IBookRepository bookRepository) {
		this.bookRepository = bookRepository;
	}
	@Override
	public Book addBook(Book book) {
		return bookRepository.save(book);
	}
	@Override
	public Book getById(int bookId) {
		return bookRepository.findById(bookId)
				   .orElseThrow(()-> new BookNotFoundException("invalid Id"));
	}
	@Override
	public List<Book> getAll() {
		return bookRepository.findAll();
	}
	@Override
	public List<Book> getByAuthor(String author) {
		List<Book> books = bookRepository.findByAuthor(author);
		if(books.isEmpty())
			throw new BookNotFoundException("author not found");
		return books;
	}
	@Override
	public List<Book> getByCategory(String category) {
		List<Book> books = bookRepository.findByCategory(category);
		if(books.isEmpty())
			throw new BookNotFoundException("category not found");
		return books;
	}
	
	
}
