package com.github.tareksaeed0.shipping;

import java.util.Collections;
import java.util.List;
import java.util.Objects;

public class ShippingDetails {
  private final List<Shippable> shippables;
  private final double totalPackageWeight;
  private final double shippingCost;

  public ShippingDetails(List<Shippable> shippables, double totalPackageWeight,
      double shippingCost) {
    this.shippables = List.copyOf(
        Objects.requireNonNull(shippables, "Shippables can't be null."));
    this.totalPackageWeight = totalPackageWeight;
    this.shippingCost = shippingCost;
  }

  public List<Shippable> getShippables() {
    return Collections.unmodifiableList(shippables);
  }

  public double getTotalPackageWeight() {
    return totalPackageWeight;
  }

  public double getShippingCost() {
    return shippingCost;
  }
}
