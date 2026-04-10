package com.github.tareksaeed0.expiration;

import java.time.LocalDateTime;
import com.github.tareksaeed0.product.Product;
import com.github.tareksaeed0.product.ProductWithInformation;

public class ExpirableProduct
    extends ProductWithInformation<ExpirationInformation> implements Expirable {
  public ExpirableProduct(Product product) {
    super(product, ExpirationInformation.class);
  }

  @Override
  public LocalDateTime getExpirationTime() {
    return getInformation(ExpirationInformation.class).getExpirationDate();
  }
}
