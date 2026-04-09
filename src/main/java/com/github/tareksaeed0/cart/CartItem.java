package com.github.tareksaeed0.cart;

import java.util.Objects;
import com.github.tareksaeed0.product.Product;

public class CartItem {
  private final Product product;
  private final int quantity;

  public CartItem(Product product, int quantity) {
    this.product = Objects.requireNonNull(product, "Product can't be null.");
    this.quantity = quantity;
  }

  public Product getProduct() {
    return product;
  }

  public int getQuantity() {
    return quantity;
  }
}
