package com.github.tareksaeed0.product;

public class ProductWithInformation<T extends ProductInformation>
    implements Product {
  private final Product product;

  public ProductWithInformation(Product product, Class<T> type) {
    if (!product.hasInformation(type)) {
      throw new IllegalArgumentException("Product must have "
          + type.getSimpleName() + " information to be wrapped");
    }

    this.product = product;
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
  public <U extends ProductInformation> U getInformation(Class<U> type) {
    return product.getInformation(type);
  }

  @Override
  public <U extends ProductInformation> boolean hasInformation(Class<U> type) {
    return product.hasInformation(type);
  }
}
