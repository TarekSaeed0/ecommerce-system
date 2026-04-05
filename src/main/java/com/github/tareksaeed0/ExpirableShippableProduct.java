package com.github.tareksaeed0;

import java.time.LocalDateTime;

public class ExpirableShippableProduct extends Product
		implements Expirable, Shippable {
	private LocalDateTime expirationTime;
	private double weight;

	public ExpirableShippableProduct(String name, float price, int quantity,
			LocalDateTime expirationTime, double weight) {
		super(name, price, quantity);

		setExpirationTime(expirationTime);
		setWeight(weight);
	}

	@Override
	public LocalDateTime getExpirationTime() {
		return expirationTime;
	}

	public void setExpirationTime(LocalDateTime expirationTime) {
		this.expirationTime = expirationTime;
	}


	@Override
	public double getWeight() {
		return weight;
	}

	public void setWeight(double weight) {
		this.weight = weight;
	}
}
