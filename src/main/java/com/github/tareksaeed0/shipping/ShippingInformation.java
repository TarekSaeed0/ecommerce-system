package com.github.tareksaeed0.shipping;

import com.github.tareksaeed0.ProductInformation;

public class ShippingInformation implements ProductInformation {
  private double weight;

  public ShippingInformation(double weight) {
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
