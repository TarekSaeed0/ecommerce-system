package com.github.tareksaeed0.shipping;

import java.util.List;
import java.util.Objects;

public class SimpleShippingService implements ShippingService {
  private final double ratePerKilogram;

  public SimpleShippingService(double ratePerKilogram) {
    if (ratePerKilogram < 0) {
      throw new IllegalArgumentException(
          "Rate per kilogram can't be negative.");
    }

    this.ratePerKilogram = ratePerKilogram;
  }

  @Override
  public ShippingDetails ship(List<Shippable> shippables) {
    Objects.requireNonNull(shippables, "Shippables can't be null.");

    double totalPackageWeight =
        shippables.stream().mapToDouble(Shippable::getWeight).sum();

    double shippingCost = totalPackageWeight * ratePerKilogram;

    return new ShippingDetails(shippables, totalPackageWeight, shippingCost);
  }
}
