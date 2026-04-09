package com.github.tareksaeed0.shipping;

import java.util.List;

public class WeightBasedShippingService implements ShippingService {
  private final double ratePerKilogram;

  public WeightBasedShippingService(double ratePerKilogram) {
    if (ratePerKilogram < 0) {
      throw new IllegalArgumentException(
          "Rate per kilogram can't be negative.");
    }

    this.ratePerKilogram = ratePerKilogram;
  }

  @Override
  public double ship(List<Shippable> items) {
    double totalWeight = items.stream().mapToDouble(Shippable::getWeight).sum();

    return totalWeight * ratePerKilogram;
  }
}
