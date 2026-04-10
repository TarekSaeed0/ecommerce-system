package com.github.tareksaeed0.checkout;

import java.util.Collections;
import java.util.List;
import java.util.Objects;
import com.github.tareksaeed0.cart.CartItem;
import com.github.tareksaeed0.shipping.ShippingDetails;

public class CheckoutDetails {
  private final ShippingDetails shippingDetails;
  private final List<CartItem> items;
  private final double subtotal;
  private final double paidAmount;
  private final double remainingBalance;

  public CheckoutDetails(ShippingDetails shippingDetails, List<CartItem> items,
      double subtotal, double paidAmount, double remainingBalance) {
    this.shippingDetails = shippingDetails;
    this.items = Objects.requireNonNull(items, "Items can't be null.");
    this.subtotal = subtotal;
    this.paidAmount = paidAmount;
    this.remainingBalance = remainingBalance;
  }

  public ShippingDetails getShippingDetails() {
    return shippingDetails;
  }

  public List<CartItem> getItems() {
    return Collections.unmodifiableList(items);
  }

  public double getSubtotal() {
    return subtotal;
  }

  public double getPaidAmount() {
    return paidAmount;
  }

  public double getRemainingBalance() {
    return remainingBalance;
  }
}
