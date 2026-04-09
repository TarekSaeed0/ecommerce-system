package com.github.tareksaeed0.checkout.receipt;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class ReceiptBuilder {
  private String title = "";
  private List<ReceiptItem> items = new ArrayList<>();
  private List<ReceiptTotal> totals = new ArrayList<>();

  public ReceiptBuilder withTitle(String title) {
    this.title = Objects.requireNonNull(title, "Title can't be null");
    return this;
  }

  public ReceiptBuilder withItems(List<ReceiptItem> items) {
    this.items.addAll(Objects.requireNonNull(items, "Items can't be null"));
    return this;
  }

  public ReceiptBuilder withItem(ReceiptItem item) {
    this.items.add(Objects.requireNonNull(item, "Item can't be null"));
    return this;
  }

  public ReceiptBuilder withItem(String name, int quantity, Object value) {
    return withItem(new ReceiptItem(name, quantity, value));
  }

  public ReceiptBuilder withTotals(List<ReceiptTotal> totals) {
    this.totals.addAll(Objects.requireNonNull(totals, "Totals can't be null"));
    return this;
  }

  public ReceiptBuilder withTotal(ReceiptTotal total) {
    this.totals.add(Objects.requireNonNull(total, "Total can't be null"));
    return this;
  }

  public ReceiptBuilder withTotal(String name, Object value) {
    return withTotal(new ReceiptTotal(name, value));
  }

  public Receipt build() {
    return new Receipt(title, items, totals);
  }
}
