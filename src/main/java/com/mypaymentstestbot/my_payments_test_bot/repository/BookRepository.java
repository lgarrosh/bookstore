package com.mypaymentstestbot.my_payments_test_bot.repository;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.mypaymentstestbot.my_payments_test_bot.entity.Book;


@Repository
public interface BookRepository extends JpaRepository<Book, Long>{
	List<Book> findAll();
}
