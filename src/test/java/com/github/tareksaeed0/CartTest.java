package com.github.tareksaeed0;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import java.util.HashMap;
import java.util.Map;
import org.junit.jupiter.api.Test;

public class CartTest {
  Product product1 = new Product("Test Product 1", 10.0, 5);
  Product product2 = new Product("Test Product 2", 15.0, 3);
  Product product3 = new Product("Test Product 3", 20.0, 2);
  Product product4 = new Product("Test Product 4", 25.0, 1);

  @Test
  public void cantAddProductWithNonPositiveQuantity() {
    Cart cart = new Cart();

    assertThrows(IllegalArgumentException.class, () -> cart.add(product1, 0));
    assertThrows(IllegalArgumentException.class, () -> cart.add(product1, -1));
  }

  @Test
  public void cantAddProductWithQuantityExceedingAvailable() {
    Cart cart = new Cart();

    assertThrows(IllegalArgumentException.class, () -> cart.add(product1, 6));
    assertThrows(IllegalArgumentException.class, () -> cart.add(product2, 4));
    assertThrows(IllegalArgumentException.class, () -> cart.add(product3, 3));
    assertThrows(IllegalArgumentException.class, () -> cart.add(product4, 2));
  }

  @Test
  public void canAddProducts() {
    Cart cart = new Cart();

    cart.add(product1, 2);
    cart.add(product2, 1);
    cart.add(product3, 2);
    cart.add(product4, 1);

    Map<Product, Integer> items = new HashMap<>();
    for (Cart.Item item : cart) {
      items.put(item.getProduct(), item.getQuantity());
    }

    assertEquals(2, items.get(product1));
    assertEquals(1, items.get(product2));
    assertEquals(2, items.get(product3));
    assertEquals(1, items.get(product4));
  }
}
