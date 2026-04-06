package com.github.tareksaeed0.expiration;

import java.time.LocalDateTime;
import com.github.tareksaeed0.ProductInformation;

public class ExpirationInformation implements ProductInformation {
  private LocalDateTime expirationDate;

  public ExpirationInformation(LocalDateTime expirationDate) {
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
