package com.github.tareksaeed0.receipt;

import java.util.Objects;

public class ReceiptItem {
  private final String name;
  private final int quantity;
  private final Object value;

  public ReceiptItem(String name, int quantity, Object value) {
    this.name = Objects.requireNonNull(name, "Name can't be null.");
    this.quantity = quantity;
    this.value = Objects.requireNonNull(value, "Value can't be null.");
  }

  public String getName() {
    return name;
  }

  public int getQuantity() {
    return quantity;
  }

  public Object getValue() {
    return value;
  }
}
