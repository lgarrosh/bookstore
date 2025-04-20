package com.bookstore.db.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.bookstore.db.entity.Book;


@Repository
public interface BookRepository extends JpaRepository<Book, Long>{
	
	List<Book> findAll();
	List<Book> findAllByOrderByIdAsc();
	Optional<Book> findById(Long id);
	Optional<Book> findByTitle(String title);
}
