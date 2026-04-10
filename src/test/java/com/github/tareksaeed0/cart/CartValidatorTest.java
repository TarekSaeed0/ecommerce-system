package com.github.tareksaeed0.cart;

import java.time.LocalDateTime;
import java.util.List;
import com.github.tareksaeed0.expiration.ExpirableProduct;
import com.github.tareksaeed0.expiration.ExpirationInformation;
import com.github.tareksaeed0.expiration.ExpiredProductsException;
import com.github.tareksaeed0.product.BasicProduct;
import com.github.tareksaeed0.product.OutOfStockProductsException;
import com.github.tareksaeed0.product.Product;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;

public class CartValidatorTest {
  @Test
  void rejectsNullAndEmptyCart() {
    assertThrows(NullPointerException.class,
        () -> CartValidator.validate(null));
    assertThrows(EmptyCartException.class,
        () -> CartValidator.validate(new Cart()));
  }

  @Test
  void rejectsExpiredProducts() {
    Product product1 = new BasicProduct("Product 1", 5, 10).withInformation(
        new ExpirationInformation(LocalDateTime.now().minusDays(1)));
    Product product2 = new BasicProduct("Product 2", 3, 20).withInformation(
        new ExpirationInformation(LocalDateTime.now().plusDays(2)));
    Cart cart = new Cart();
    cart.add(product1, 1);
    cart.add(product2, 4);

    ExpiredProductsException exception = assertThrows(
        ExpiredProductsException.class, () -> CartValidator.validate(cart));

    assertEquals(List.of(product1), exception.getExpiredProducts().stream()
        .map(ExpirableProduct::getProduct).toList());
  }

  @Test
  void rejectsOutOfStockProducts() {
    Product product1 = new BasicProduct("Product 1", 2, 2);
    Product product2 = new BasicProduct("Product 2", 3, 5);
    Cart cart = new Cart();
    cart.add(product1, 2);
    cart.add(product2, 3);
    product1.reduceQuantity(1);

    OutOfStockProductsException exception = assertThrows(
        OutOfStockProductsException.class, () -> CartValidator.validate(cart));

    assertEquals(List.of(product1), exception.getOutOfStockProducts());
  }

  @Test
  void acceptsValidCart() {
    Product product1 = new BasicProduct("Product 1", 2, 2);
    Cart cart = new Cart();
    cart.add(product1, 2);

    assertDoesNotThrow(() -> CartValidator.validate(cart));
  }
}
