package com.github.tareksaeed0;

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
import com.github.tareksaeed0.receipt.SimpleReceiptGenerator;
import com.github.tareksaeed0.receipt.SimpleReceiptFormatter;
import com.github.tareksaeed0.shipping.ShippingInformation;
import com.github.tareksaeed0.shipping.ShippingService;
import com.github.tareksaeed0.shipping.SimpleShippingService;

public class App {
	public static void main(String[] args) {
		Product laptop = new BasicProduct("Laptop", 999.99, 10)
				.withInformation(new ShippingInformation(2.5));

		Product cheese = new BasicProduct("Cheese", 5.99, 20)
				.withInformation(
						new ExpirationInformation(LocalDateTime.now().plusDays(7)))
				.withInformation(new ShippingInformation(0.5));

		Product Tomato = new BasicProduct("Tomato", 2.99, 50)
				.withInformation(
						new ExpirationInformation(LocalDateTime.now().plusDays(3)))
				.withInformation(new ShippingInformation(0.2));

		Product scratchCard = new BasicProduct("Scratch Card", 1.00, 100);

		ShippingService shippingService = new SimpleShippingService(10);
		CheckoutService checkoutService =
				new SimpleCheckoutService(shippingService);

		ReceiptGenerator receiptGenerator = new SimpleReceiptGenerator();
		ReceiptFormatter receiptFormatter = new SimpleReceiptFormatter();

		try {
			Customer customer = new Customer("Tarek", 1500);

			Cart cart = new Cart();

			cart.add(laptop, 1);
			cart.add(cheese, 1);
			cart.add(Tomato, 5);
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
