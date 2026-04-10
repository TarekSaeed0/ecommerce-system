package com.github.tareksaeed0.product;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

public class BasicProductTest {
  private static class MockProductInformation implements ProductInformation {
  }

  @Test
  void cantCreateProductWithNullOrBlankName() {
    assertThrows(IllegalArgumentException.class,
        () -> new BasicProduct(null, 10, 1));
    assertThrows(IllegalArgumentException.class,
        () -> new BasicProduct("", 10, 1));
    assertThrows(IllegalArgumentException.class,
        () -> new BasicProduct("   ", 10, 1));
  }

  @Test
  void cantCreateProductWithNegativePrice() {
    assertThrows(IllegalArgumentException.class,
        () -> new BasicProduct("Product", -1, 1));
    assertThrows(IllegalArgumentException.class,
        () -> new BasicProduct("Product", -5, 1));
  }

  @Test
  void cantCreateProductWithNegativeQuantity() {
    assertThrows(IllegalArgumentException.class,
        () -> new BasicProduct("Product", 10, -1));
    assertThrows(IllegalArgumentException.class,
        () -> new BasicProduct("Product", 10, -100));
  }

  @Test
  void canCreateProduct() {
    BasicProduct product = new BasicProduct("Product", 10, 5);

    assertEquals("Product", product.getName());
    assertEquals(10, product.getPrice(), 0.0001);
    assertEquals(5, product.getQuantity());
  }

  @Test
  void cantReduceQuantityByNegativeAmountOrMoreThanAvailable() {
    BasicProduct product = new BasicProduct("Product", 10, 2);

    assertThrows(IllegalArgumentException.class,
        () -> product.reduceQuantity(-1));
    assertThrows(IllegalArgumentException.class,
        () -> product.reduceQuantity(3));
  }

  @Test
  void canReduceQuantity() {
    BasicProduct product = new BasicProduct("Product", 10, 5);

    product.reduceQuantity(2);

    assertEquals(3, product.getQuantity());
  }

  @Test
  void canManageInformation() {
    BasicProduct product = new BasicProduct("Product", 10, 2);
    MockProductInformation information = new MockProductInformation();

    assertFalse(product.hasInformation(MockProductInformation.class));

    product.addInformation(information);

    assertTrue(product.hasInformation(MockProductInformation.class));
    assertSame(information,
        product.getInformation(MockProductInformation.class));
    assertSame(product, product.withInformation(information));

    product.removeInformation(MockProductInformation.class);

    assertFalse(product.hasInformation(MockProductInformation.class));
  }
}
