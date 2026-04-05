package com.github.tareksaeed0;

import java.time.LocalDateTime;

public class ExpirableProduct extends Product implements Expirable {
	private LocalDateTime expirationTime;

	public ExpirableProduct(String name, float price, int quantity,
			LocalDateTime expirationTime) {
		super(name, price, quantity);

		setExpirationTime(expirationTime);
	}

	@Override
	public LocalDateTime getExpirationTime() {
		return expirationTime;
	}

	public void setExpirationTime(LocalDateTime expirationTime) {
		this.expirationTime = expirationTime;
	}
}
