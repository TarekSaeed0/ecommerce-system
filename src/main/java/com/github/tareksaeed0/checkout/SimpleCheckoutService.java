package com.github.tareksaeed0.checkout;

import java.util.List;
import java.util.Objects;
import com.github.tareksaeed0.cart.Cart;
import com.github.tareksaeed0.cart.CartItem;
import com.github.tareksaeed0.cart.CartValidator;
import com.github.tareksaeed0.customer.Customer;
import com.github.tareksaeed0.shipping.Shippable;
import com.github.tareksaeed0.shipping.ShippableItem;
import com.github.tareksaeed0.shipping.ShippableProduct;
import com.github.tareksaeed0.shipping.ShippingDetails;
import com.github.tareksaeed0.shipping.ShippingInformation;
import com.github.tareksaeed0.shipping.ShippingService;

public class SimpleCheckoutService implements CheckoutService {
	private ShippingService shippingService;

	public SimpleCheckoutService(ShippingService shippingService) {
		this.shippingService = shippingService;
	}

	private List<Shippable> getShippables(Cart cart) {
		return cart.stream()
				.filter(
						item -> item.getProduct().hasInformation(ShippingInformation.class))
				.map(item -> (Shippable) new ShippableItem(
						new ShippableProduct(item.getProduct()), item.getQuantity()))
				.toList();
	}

	private double calculateSubtotal(Cart cart) {
		return cart.stream()
				.mapToDouble(item -> item.getProduct().getPrice() * item.getQuantity())
				.sum();
	}

	@Override
	public CheckoutDetails checkout(Customer customer, Cart cart) {
		Objects.requireNonNull(customer, "Customer can't be null.");
		Objects.requireNonNull(cart, "Cart can't be null.");

		CartValidator.validate(cart);

		List<Shippable> shippables = getShippables(cart);

		ShippingDetails shippingDetails = null;
		if (!shippables.isEmpty()) {
			shippingDetails = shippingService.ship(shippables);
		}

		double subtotal = calculateSubtotal(cart);

		double amount = subtotal;
		if (shippingDetails != null) {
			amount += shippingDetails.getShippingCost();
		}

		customer.withdraw(amount);
		double remainingBalance = customer.getBalance();

		for (CartItem item : cart) {
			item.getProduct().reduceQuantity(item.getQuantity());
		}

		return new CheckoutDetails(shippingDetails, cart.stream().toList(),
				subtotal, amount, remainingBalance);
	}
}
