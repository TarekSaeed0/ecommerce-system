package com.github.tareksaeed0.shipping;

import com.github.tareksaeed0.product.ProductInformation;

public class ShippingInformation implements ProductInformation {
  private double weight;

  public ShippingInformation(double weight) {
    if (weight < 0) {
      throw new IllegalArgumentException("Weight can't be negative");
    }

    this.weight = weight;
  }

  @Override
  public String getName() {
    return "Shipping Information";
  }

  public double getWeight() {
    return weight;
  }
}
