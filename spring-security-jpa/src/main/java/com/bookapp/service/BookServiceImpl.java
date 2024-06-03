package com.bookapp.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bookapp.exceptions.BookNotFoundException;
import com.bookapp.model.Book;
import com.bookapp.repository.IBookRepository;

@Service
public class BookServiceImpl implements IBookService {

	private IBookRepository bookRepository;

	@Autowired
	public void setBookRepository(IBookRepository bookRepository) {
		this.bookRepository = bookRepository;
	}

	@Override
	public Book addBook(Book book) {
		Book nbook = bookRepository.save(book);
		return nbook;
	}

	@Override
	public Book getById(int bookId) {

		Optional<Book> bookOpt = bookRepository.findById(bookId);
		return bookOpt.orElseThrow(() -> new BookNotFoundException("Invalid Id"));
	}

	@Override
	public List<Book> getAll() {
		List<Book> books = bookRepository.findAll();
		return books;
	}

	@Override
	public List<Book> getByAuthor(String author) {
		return bookRepository.getByAuthor(author);
	}

	@Override
	public List<Book> getByCategory(String category) {
		return bookRepository.readByCategory(category);
	}

}
