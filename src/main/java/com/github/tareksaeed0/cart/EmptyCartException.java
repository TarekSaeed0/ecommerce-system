package com.github.tareksaeed0.cart;

public class EmptyCartException extends RuntimeException {
	public EmptyCartException() {
		super("The cart is empty.");
	}
}

