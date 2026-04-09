package com.github.tareksaeed0.expiration;

import java.time.LocalDateTime;
import java.util.Objects;
import com.github.tareksaeed0.product.ProductInformation;

public class ExpirationInformation implements ProductInformation {
  private LocalDateTime expirationDate;

  public ExpirationInformation(LocalDateTime expirationDate) {
    Objects.requireNonNull(expirationDate, "Expiration date can't be null.");

    this.expirationDate = expirationDate;
  }

  @Override
  public String getName() {
    return "Expiration Information";
  }

  public LocalDateTime getExpirationDate() {
    return expirationDate;
  }
}
