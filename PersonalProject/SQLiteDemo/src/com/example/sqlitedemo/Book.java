/**
 * 创建一个书类
 * 包含了id name price
 */
package com.example.sqlitedemo;

public class Book {
	public int id;
	public String name;
	public int price;
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public int getPrice() {
		return price;
	}
	public void setPrice(int price) {
		this.price = price;
	}
	

}
