package com.mypaymentstestbot.my_payments_test_bot.entity;

import java.sql.Date;


import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "books")
public class Book {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@Column(nullable = false)
	private String title;
	
	@Column(nullable = false)
	private String author;
	
	private Integer price;
	
	@Column(nullable = false)
	private String filePath;
	
	private Date createdAt;
	
	public Long getId() {return id;}
	public void setId(Long id) {this.id = id;}
	public String getTitle() {return title;}
	public void setTitle(String title) {this.title = title;}
	public String getAuthor() {return author;}
	public void setAuthor(String author) {this.author = author;}
	public Integer getPrice() {return price;}
	public void setPrice(Integer price) {this.price = price;}
	public String getFilePath() {return filePath;}
	public void setFilePath(String filePath) {this.filePath = filePath;}
	public Date getCreatedAt() {return createdAt;}
	public void setCreatedAt(Date createdAt) {this.createdAt = createdAt;}
	
	
	@Override
	public String toString() {
		return "Book [id=" + id + ", title=" + title + ", author=" + author + ", price=" + price + ", filePath="
				+ filePath + ", createdAt=" + createdAt + "]";
	}
	
	
}
