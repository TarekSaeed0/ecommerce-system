package com.github.tareksaeed0;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.stream.Stream;
import com.github.tareksaeed0.product.Product;

public class Cart implements Iterable<Cart.Item> {
	public class Item {
		private final Product product;
		private final int quantity;

		public Item(Product product, int quantity) {
			this.product = product;
			this.quantity = quantity;
		}

		public Product getProduct() {
			return product;
		}

		public int getQuantity() {
			return quantity;
		}
	}

	Map<Product, Integer> items = new HashMap<>();

	public void add(Product product, int quantity) {
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

	public Stream<Item> stream() {
		return items.entrySet().stream()
				.map(entry -> new Item(entry.getKey(), entry.getValue()));
	}

	@Override
	public Iterator<Item> iterator() {
		return items.entrySet().stream()
				.map(entry -> new Item(entry.getKey(), entry.getValue())).iterator();
	}
}
