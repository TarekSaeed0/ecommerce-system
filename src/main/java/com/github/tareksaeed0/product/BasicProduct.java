package com.github.tareksaeed0.product;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

public class BasicProduct implements Product {
	private String name;
	private double price;
	private int quantity;
	private Map<Class<? extends ProductInformation>, ProductInformation> informations =
			new HashMap<>();

	public BasicProduct(String name, double price, int quantity) {
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

	@Override
	public void reduceQuantity(int amount) {
		if (amount < 0) {
			throw new IllegalArgumentException("Amount can't be negative.");
		}

		if (amount > quantity) {
			throw new IllegalArgumentException(
					"Amount can't exceed the available quantity.");
		}

		quantity -= amount;
	}


	public <T extends ProductInformation> T getInformation(Class<T> type) {
		Objects.requireNonNull(type, "Information type can't be null.");

		return type.cast(informations.get(type));
	}

	public <T extends ProductInformation> boolean hasInformation(Class<T> type) {
		Objects.requireNonNull(type, "Information type can't be null.");

		return informations.containsKey(type);
	}

	public void addInformation(ProductInformation information) {
		Objects.requireNonNull(information, "Information can't be null.");

		informations.put(information.getClass(), information);
	}

	public <T extends ProductInformation> void removeInformation(Class<T> type) {
		Objects.requireNonNull(type, "Information type can't be null.");

		informations.remove(type);
	}

	List<ProductInformation> getInformations() {
		return informations.values().stream().toList();
	}

	public <T extends ProductInformation> BasicProduct withInformation(
			T information) {
		addInformation(information);
		return this;
	}
}
