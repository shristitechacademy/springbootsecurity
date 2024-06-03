package com.bookapp.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.bookapp.model.Book;

@Repository
public interface IBookRepository extends JpaRepository<Book, Integer>{

	List<Book> findByAuthor(String author);
	List<Book> findByCategory(String category);
}
