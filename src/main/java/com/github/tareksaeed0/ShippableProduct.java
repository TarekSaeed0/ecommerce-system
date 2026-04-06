package com.github.tareksaeed0;

public class ShippableProduct extends Product implements Shippable {
	private double weight;

	public ShippableProduct(String name, double price, int quantity,
			double weight) {
		super(name, price, quantity);

		setWeight(weight);
	}

	@Override
	public double getWeight() {
		return weight;
	}

	public void setWeight(double weight) {
		this.weight = weight;
	}
}
