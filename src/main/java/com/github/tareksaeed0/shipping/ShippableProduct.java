package com.github.tareksaeed0.shipping;

import com.github.tareksaeed0.Product;

public class ShippableProduct implements Shippable {
  private final Product product;

  public ShippableProduct(Product product) {
    if (!product.hasInformation(ShippingInformation.class)) {
      throw new IllegalArgumentException(
          "Product must have shipping information to be shippable");
    }

    this.product = product;
  }

  public Product getProduct() {
    return product;
  }

  @Override
  public String getName() {
    return product.getName();
  }

  @Override
  public double getWeight() {
    return product.getInformation(ShippingInformation.class).getWeight();
  }
}
