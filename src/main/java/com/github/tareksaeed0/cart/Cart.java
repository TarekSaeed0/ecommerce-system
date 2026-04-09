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
			throw new IllegalArgumentException("Quantity must be positive.");
		}

		int totalQuantity = items.getOrDefault(product, 0) + quantity;
		if (totalQuantity > product.getQuantity()) {
			throw new IllegalArgumentException(
					"Quantity can't exceed the available product quantity.");
		}

		items.put(product, totalQuantity);
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
