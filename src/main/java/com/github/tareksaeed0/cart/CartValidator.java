package com.github.tareksaeed0.cart;

import java.util.List;
import java.util.Objects;
import com.github.tareksaeed0.expiration.ExpirableProduct;
import com.github.tareksaeed0.expiration.ExpirationInformation;
import com.github.tareksaeed0.expiration.ExpiredProductsException;
import com.github.tareksaeed0.product.OutOfStockProductsException;
import com.github.tareksaeed0.product.Product;

public class CartValidator {
  public static void validate(Cart cart) {
    Objects.requireNonNull(cart, "Cart can't be null.");

    if (cart.isEmpty()) {
      throw new EmptyCartException();
    }

    List<ExpirableProduct> expiredProducts = getExpiredProducts(cart);
    if (!expiredProducts.isEmpty()) {
      throw new ExpiredProductsException(expiredProducts);
    }

    List<Product> outOfStockProducts = getOutOfStockProducts(cart);
    if (!outOfStockProducts.isEmpty()) {
      throw new OutOfStockProductsException(outOfStockProducts);
    }
  }

  private static List<ExpirableProduct> getExpiredProducts(Cart cart) {
    return cart.stream()
        .filter(item -> item.getProduct()
            .hasInformation(ExpirationInformation.class))
        .map(item -> new ExpirableProduct(item.getProduct()))
        .filter(ExpirableProduct::isExpired).toList();
  }

  private static List<Product> getOutOfStockProducts(Cart cart) {
    return cart.stream()
        .filter(item -> item.getProduct().getQuantity() < item.getQuantity())
        .map(CartItem::getProduct).toList();
  }
}
