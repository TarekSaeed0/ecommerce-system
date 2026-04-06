package com.github.tareksaeed0;

public class Product {
	private String name;
	private double price;
	private int quantity;

	public Product(String name, double price, int quantity) {
		setName(name);
		setPrice(price);
		setQuantity(quantity);
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		if (name == null || name.trim().isEmpty()) {
			throw new IllegalArgumentException("Name can't be null or empty.");
		}

		this.name = name;
	}

	public double getPrice() {
		return price;
	}

	public void setPrice(double price) {
		if (price < 0) {
			throw new IllegalArgumentException("Price can't be negative.");
		}

		this.price = price;
	}

	public int getQuantity() {
		return quantity;
	}

	public void setQuantity(int quantity) {
		if (quantity < 0) {
			throw new IllegalArgumentException("Quantity can't be negative.");
		}

		this.quantity = quantity;
	}
}
