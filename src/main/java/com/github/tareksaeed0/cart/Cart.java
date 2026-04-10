package com.github.tareksaeed0.cart;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Stream;
import com.github.tareksaeed0.product.Product;

public class Cart implements Iterable<CartItem> {
	private final Map<Product, Integer> items = new HashMap<>();

	public void add(Product product, int quantity) {
		Objects.requireNonNull(product, "Product can't be null.");

		if (quantity <= 0) {
			throw new IllegalArgumentException("Quantity can't be non-positive.");
		}

		int totalQuantity = items.getOrDefault(product, 0) + quantity;
		if (totalQuantity > product.getQuantity()) {
			throw new IllegalArgumentException(
					"Quantity can't exceed the available product quantity.");
		}

		items.put(product, totalQuantity);
	}

	public void remove(Product product, int quantity) {
		Objects.requireNonNull(product, "Product can't be null.");

		if (quantity <= 0) {
			throw new IllegalArgumentException("Quantity can't be non-positive.");
		}

		if (items.getOrDefault(product, 0) < quantity) {
			throw new IllegalArgumentException(
					"Quantity to remove can't exceed the quantity in the cart.");
		}

		if (items.get(product) == quantity) {
			items.remove(product);
		} else {
			items.put(product, items.get(product) - quantity);
		}
	}

	public void clear() {
		items.clear();
	}

	public boolean isEmpty() {
		return items.isEmpty();
	}

	public Stream<CartItem> stream() {
		return items.entrySet().stream()
				.map(entry -> new CartItem(entry.getKey(), entry.getValue()));
	}

	@Override
	public Iterator<CartItem> iterator() {
		return items.entrySet().stream()
				.map(entry -> new CartItem(entry.getKey(), entry.getValue()))
				.iterator();
	}
}
