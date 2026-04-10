package com.github.tareksaeed0.examples;

import java.util.List;
import com.github.tareksaeed0.cart.Cart;
import com.github.tareksaeed0.checkout.CheckoutDetails;
import com.github.tareksaeed0.checkout.CheckoutService;
import com.github.tareksaeed0.checkout.SimpleCheckoutService;
import com.github.tareksaeed0.customer.Customer;
import com.github.tareksaeed0.receipt.Receipt;
import com.github.tareksaeed0.receipt.ReceiptFormatter;
import com.github.tareksaeed0.receipt.ReceiptGenerator;
import com.github.tareksaeed0.receipt.SimpleReceiptFormatter;
import com.github.tareksaeed0.receipt.SimpleReceiptGenerator;
import com.github.tareksaeed0.shipping.ShippingService;
import com.github.tareksaeed0.shipping.SimpleShippingService;

public class Example5 implements Example {
  @Override
  public String getName() {
    return "Example 5: Empty Cart";
  }

  @Override
  public void run() {
    ShippingService shippingService = new SimpleShippingService(10);
    CheckoutService checkoutService =
        new SimpleCheckoutService(shippingService);

    ReceiptGenerator receiptGenerator = new SimpleReceiptGenerator();
    ReceiptFormatter receiptFormatter = new SimpleReceiptFormatter();

    try {
      Customer customer = new Customer("Tarek", 1500);

      Cart cart = new Cart();

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
