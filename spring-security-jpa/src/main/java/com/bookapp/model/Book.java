package com.bookapp.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.SequenceGenerator;

@Entity 
public class Book {

	@Column(length = 30)
	private String title;
	@Column(length = 30)
	private String author;
	// this property is the primary key
	@Id 
	@GeneratedValue(generator = "book_gen",strategy = GenerationType.AUTO)
	@SequenceGenerator(name = "book_gen",sequenceName = "book_seq",initialValue = 50,allocationSize = 1)
	private Integer bookId;
	@Column(name="cost")
	private double price;
	@Column(length = 30)
	private String category;
	public Book() {
		super();
		// TODO Auto-generated constructor stub
	}
	public Book(String title, String author,  double price, String category) {
		super();
		this.title = title;
		this.author = author;
//		this.bookId = bookId;
		this.price = price;
		this.category = category;
	}
	
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getAuthor() {
		return author;
	}
	public void setAuthor(String author) {
		this.author = author;
	}
	public Integer getBookId() {
		return bookId;
	}
	public void setBookId(Integer bookId) {
		this.bookId = bookId;
	}
	public double getPrice() {
		return price;
	}
	public void setPrice(double price) {
		this.price = price;
	}
	public String getCategory() {
		return category;
	}
	public void setCategory(String category) {
		this.category = category;
	}
	@Override
	public String toString() {
		return "Book [title=" + title + ", author=" + author + ", bookId=" + bookId + ", price=" + price + ", category="
				+ category + "]";
	}
	
	

}
