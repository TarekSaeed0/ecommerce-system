package com.github.tareksaeed0.expiration;

import java.util.Collections;
import java.util.List;

public class ExpiredProductsException extends RuntimeException {
	private final List<ExpirableProduct> expiredProducts;

	public ExpiredProductsException(List<ExpirableProduct> expiredProducts) {
		super("The following products have expired: " + String.join(", ",
				expiredProducts.stream().map(ExpirableProduct::getName).toList()));

		this.expiredProducts = expiredProducts;
	}

	public List<ExpirableProduct> getExpiredProducts() {
		return Collections.unmodifiableList(expiredProducts);
	}
}
