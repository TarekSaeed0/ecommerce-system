package com.github.tareksaeed0.expiration;

import java.time.LocalDateTime;
import com.github.tareksaeed0.Product;

public class ExpirableProduct implements Expirable {
  private final Product product;

  public ExpirableProduct(Product product) {
    if (!product.hasInformation(ExpirationInformation.class)) {
      throw new IllegalArgumentException(
          "Product must have expiration information to be expirable");
    }

    this.product = product;
  }

  public Product getProduct() {
    return product;
  }

  @Override
  public LocalDateTime getExpirationTime() {
    return product.getInformation(ExpirationInformation.class)
        .getExpirationDate();
  }
}
