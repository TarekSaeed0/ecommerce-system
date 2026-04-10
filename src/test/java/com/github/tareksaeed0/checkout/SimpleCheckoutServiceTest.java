package com.github.tareksaeed0.checkout;

import java.time.LocalDateTime;
import java.util.List;
import com.github.tareksaeed0.cart.Cart;
import com.github.tareksaeed0.cart.EmptyCartException;
import com.github.tareksaeed0.customer.Customer;
import com.github.tareksaeed0.expiration.ExpirationInformation;
import com.github.tareksaeed0.expiration.ExpiredProductsException;
import com.github.tareksaeed0.product.BasicProduct;
import com.github.tareksaeed0.product.OutOfStockProductsException;
import com.github.tareksaeed0.product.Product;
import com.github.tareksaeed0.shipping.Shippable;
import com.github.tareksaeed0.shipping.ShippingDetails;
import com.github.tareksaeed0.shipping.ShippingInformation;
import com.github.tareksaeed0.shipping.ShippingService;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;

public class SimpleCheckoutServiceTest {
  private static class MockShippingService implements ShippingService {
    @Override
    public ShippingDetails ship(List<Shippable> shippables) {
      return new ShippingDetails(shippables, 3.0, 100.0);
    }
  }

  @Test
  void cantCheckoutWithInvalidCart() {
    SimpleCheckoutService service =
        new SimpleCheckoutService(new MockShippingService());

    Customer customer = new Customer("Tarek", 500);

    Cart cart = new Cart();
    assertThrows(EmptyCartException.class,
        () -> service.checkout(customer, cart));

    Product expiredProduct =
        new BasicProduct("Expired Product", 10, 1).withInformation(
            new ExpirationInformation(LocalDateTime.now().minusDays(1)));

    cart.add(expiredProduct, 1);
    assertThrows(ExpiredProductsException.class,
        () -> service.checkout(customer, cart));

    cart.clear();

    Product outOfStockProduct = new BasicProduct("Out of Stock Product", 10, 5);
    cart.add(outOfStockProduct, 1);
    outOfStockProduct.reduceQuantity(5);
    assertThrows(OutOfStockProductsException.class,
        () -> service.checkout(customer, cart));

  }

  @Test
  void checkingOutCartAndUpdatesCustomerAndProducts() {
    SimpleCheckoutService service =
        new SimpleCheckoutService(new MockShippingService());

    BasicProduct shippableProduct =
        new BasicProduct("Shippable Product", 100, 3)
            .withInformation(new ShippingInformation(1.5));
    BasicProduct nonShippableProduct =
        new BasicProduct("Non-Shippable Product", 20, 5);

    Cart cart = new Cart();
    cart.add(shippableProduct, 2);
    cart.add(nonShippableProduct, 1);

    Customer customer = new Customer("Tarek", 500);

    CheckoutDetails details = service.checkout(customer, cart);

    assertNotNull(details.getShippingDetails());
    assertEquals(2, details.getItems().size());
    assertEquals(220.0, details.getSubtotal(), 0.0001);
    assertEquals(320.0, details.getPaidAmount(), 0.0001);
    assertEquals(180.0, details.getRemainingBalance(), 0.0001);
    assertEquals(180.0, customer.getBalance(), 0.0001);
    assertEquals(1, shippableProduct.getQuantity());
    assertEquals(4, nonShippableProduct.getQuantity());
  }
}
