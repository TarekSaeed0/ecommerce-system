package com.github.tareksaeed0.product;

import java.util.Objects;

public class ProductWithInformation<T extends ProductInformation>
    implements Product {
  private final Product product;

  public ProductWithInformation(Product product, Class<T> type) {
    this.product = Objects.requireNonNull(product, "Product can't be null.");

    Objects.requireNonNull(type, "Information type can't be null.");

    if (!product.hasInformation(type)) {
      throw new IllegalArgumentException("Product must have "
          + type.getSimpleName() + " information to be wrapped");
    }
  }

  public Product getProduct() {
    return product;
  }

  @Override
  public String getName() {
    return product.getName();
  }

  @Override
  public double getPrice() {
    return product.getPrice();
  }

  @Override
  public int getQuantity() {
    return product.getQuantity();
  }

  @Override
  public void reduceQuantity(int amount) {
    product.reduceQuantity(amount);
  }

  @Override
  public <U extends ProductInformation> U getInformation(Class<U> type) {
    return product.getInformation(type);
  }

  @Override
  public <U extends ProductInformation> boolean hasInformation(Class<U> type) {
    return product.hasInformation(type);
  }
}
