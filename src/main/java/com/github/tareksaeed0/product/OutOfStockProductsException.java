package com.github.tareksaeed0.product;

import java.util.List;

public class OutOfStockProductsException extends RuntimeException {
	private final List<Product> outOfStockProducts;

	public OutOfStockProductsException(List<Product> outOfStockProducts) {
		super("The following products are out of stock: " + String.join(", ",
				outOfStockProducts.stream().map(Product::getName).toList()));
		this.outOfStockProducts = outOfStockProducts;
	}

	public List<Product> getOutOfStockProducts() {
		return outOfStockProducts;
	}
}
