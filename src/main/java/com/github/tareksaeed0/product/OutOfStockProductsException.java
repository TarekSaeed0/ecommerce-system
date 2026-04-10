package com.github.tareksaeed0.product;

import java.util.Collections;
import java.util.List;

public class OutOfStockProductsException extends RuntimeException {
	private final List<Product> outOfStockProducts;

	public OutOfStockProductsException(List<Product> outOfStockProducts) {
		super("The following products are out of stock: " + String.join(", ",
				outOfStockProducts.stream().map(Product::getName).toList()));

		this.outOfStockProducts = List.copyOf(outOfStockProducts);
	}

	public List<Product> getOutOfStockProducts() {
		return Collections.unmodifiableList(outOfStockProducts);
	}
}
