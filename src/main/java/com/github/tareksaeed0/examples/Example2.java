package com.github.tareksaeed0.examples;

import java.time.LocalDateTime;
import java.util.List;
import com.github.tareksaeed0.cart.Cart;
import com.github.tareksaeed0.checkout.CheckoutDetails;
import com.github.tareksaeed0.checkout.CheckoutService;
import com.github.tareksaeed0.checkout.SimpleCheckoutService;
import com.github.tareksaeed0.customer.Customer;
import com.github.tareksaeed0.expiration.ExpirationInformation;
import com.github.tareksaeed0.product.BasicProduct;
import com.github.tareksaeed0.product.Product;
import com.github.tareksaeed0.receipt.Receipt;
import com.github.tareksaeed0.receipt.ReceiptFormatter;
import com.github.tareksaeed0.receipt.ReceiptGenerator;
import com.github.tareksaeed0.receipt.SimpleReceiptFormatter;
import com.github.tareksaeed0.receipt.SimpleReceiptGenerator;
import com.github.tareksaeed0.shipping.ShippingService;
import com.github.tareksaeed0.shipping.SimpleShippingService;

public class Example2 implements Example {
  @Override
  public String getName() {
    return "Example 2: No Shipping Required";
  }

  @Override
  public void run() {
    ShippingService shippingService = new SimpleShippingService(10);
    CheckoutService checkoutService =
        new SimpleCheckoutService(shippingService);

    ReceiptGenerator receiptGenerator = new SimpleReceiptGenerator();
    ReceiptFormatter receiptFormatter = new SimpleReceiptFormatter();

    Product subscription =
        new BasicProduct("Subscription", 20.00, 100).withInformation(
            new ExpirationInformation(LocalDateTime.now().plusMonths(1)));

    Product scratchCard = new BasicProduct("Scratch Card", 1.00, 100);

    try {
      Customer customer = new Customer("Tarek", 1500);

      Cart cart = new Cart();

      cart.add(subscription, 1);
      cart.add(scratchCard, 2);

      CheckoutDetails details = checkoutService.checkout(customer, cart);

      List<Receipt> receipts = receiptGenerator.generateReceipts(details);
      for (Receipt receipt : receipts) {
        System.out.println(receiptFormatter.format(receipt));
      }
    } catch (Exception e) {
      System.out.println("Error: " + e.getMessage());
    }
  }
}
