package com.bookstore.db;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.bookstore.db.entity.Book;
import com.bookstore.db.repository.BookRepository;

@Service
public class BookService {
	private BookRepository bookRepository;

	public BookService(BookRepository bookRepository) {
		this.bookRepository = bookRepository;
	}
	
	public List<Book> getAllBooks() {
		return bookRepository.findAll();
	}
	
	public List<Book> getAllByOrderByIdAsc() {
		return bookRepository.findAllByOrderByIdAsc();
	}
	
	public Optional<Book> getBookById(Long id) {
		Optional<Book> book = bookRepository.findById(id);
		return book;
	}
}
