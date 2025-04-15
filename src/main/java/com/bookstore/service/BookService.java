package com.bookstore.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.bookstore.entity.Book;
import com.bookstore.repository.BookRepository;

@Service
public class BookService {
	private BookRepository bookRepository;

	public BookService(BookRepository bookRepository) {
		this.bookRepository = bookRepository;
	}
	
	public List<Book> getAllBooks() {
		return bookRepository.findAll();
	}
	
	public Book getBookById(int id) {
		List<Book> myList = bookRepository.findAll();
		Book myBook = myList.get((int) (id-1));
		return myBook;
	}
}
