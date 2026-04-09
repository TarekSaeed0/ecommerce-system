package com.github.tareksaeed0.checkout.receipt;

import java.util.Objects;

public class ReceiptTotal {
  private final String name;
  private final Object value;

  public ReceiptTotal(String name, Object value) {
    this.name = Objects.requireNonNull(name, "Name can't be null.");
    this.value = Objects.requireNonNull(value, "Value can't be null.");
  }

  public Object getValue() {
    return value;
  }

  public String getName() {
    return name;
  }
}
