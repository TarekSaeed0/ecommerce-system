package com.github.tareksaeed0.shipping;

import java.util.Objects;

public class ShippableItem implements Shippable {
	private final ShippableProduct product;
	private final int quantity;

	public ShippableItem(ShippableProduct product, int quantity) {
		this.product = Objects.requireNonNull(product, "Product can't be null.");

		if (quantity <= 0) {
			throw new IllegalArgumentException("Quantity can't be non-positive.");
		}

		this.quantity = quantity;
	}

	public ShippableProduct getProduct() {
		return product;
	}

	public int getQuantity() {
		return quantity;
	}

	@Override
	public String getName() {
		return product.getName();
	}

	@Override
	public double getWeight() {
		return product.getWeight() * quantity;
	}
}
