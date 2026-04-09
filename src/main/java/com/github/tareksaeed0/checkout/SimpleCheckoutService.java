package com.github.tareksaeed0.checkout;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import com.github.tareksaeed0.cart.Cart;
import com.github.tareksaeed0.cart.CartItem;
import com.github.tareksaeed0.cart.CartValidator;
import com.github.tareksaeed0.checkout.receipt.ReceiptItem;
import com.github.tareksaeed0.checkout.receipt.Receipt;
import com.github.tareksaeed0.checkout.receipt.ReceiptBuilder;
import com.github.tareksaeed0.customer.Customer;
import com.github.tareksaeed0.shipping.Shippable;
import com.github.tareksaeed0.shipping.ShippableItem;
import com.github.tareksaeed0.shipping.ShippableProduct;
import com.github.tareksaeed0.shipping.ShippingInformation;
import com.github.tareksaeed0.shipping.ShippingService;
import com.github.tareksaeed0.units.Money;
import com.github.tareksaeed0.units.Weight;

public class SimpleCheckoutService implements CheckoutService {
	private ShippingService shippingService;

	public SimpleCheckoutService(ShippingService shippingService) {
		this.shippingService = shippingService;
	}

	private List<ShippableItem> getShippableItems(Cart cart) {
		return cart.stream()
				.filter(
						item -> item.getProduct().hasInformation(ShippingInformation.class))
				.map(item -> new ShippableItem(new ShippableProduct(item.getProduct()),
						item.getQuantity()))
				.toList();
	}

	private double calculateSubtotal(Cart cart) {
		return cart.stream()
				.mapToDouble(item -> item.getProduct().getPrice() * item.getQuantity())
				.sum();
	}

	private double calculateShipping(List<ShippableItem> shippableItems) {
		return shippingService
				.ship(shippableItems.stream().map(item -> (Shippable) item).toList());
	}

	@Override
	public List<Receipt> checkout(Customer customer, Cart cart) {
		Objects.requireNonNull(customer, "Customer can't be null.");
		Objects.requireNonNull(cart, "Cart can't be null.");

		CartValidator.validate(cart);

		List<Receipt> receipts = new ArrayList<>();

		List<ShippableItem> shippableItems = getShippableItems(cart);

		if (!shippableItems.isEmpty()) {
			double totalPackageWeight =
					shippableItems.stream().mapToDouble(Shippable::getWeight).sum();

			Receipt shippingReceipt = new ReceiptBuilder()
					.withTitle("Shipping Notice")
					.withItems(shippableItems.stream()
							.map(item -> new ReceiptItem(item.getName(), item.getQuantity(),
									new Weight(item.getWeight())))
							.toList())
					.withTotal("Total Package Weight", new Weight(totalPackageWeight))
					.build();

			receipts.add(shippingReceipt);
		}

		double subtotal = calculateSubtotal(cart);

		ReceiptBuilder paymentReceiptBuilder = new ReceiptBuilder()
				.withTitle("Checkout Receipt")
				.withItems(cart.stream()
						.map(item -> new ReceiptItem(item.getProduct().getName(),
								item.getQuantity(),
								new Money(item.getProduct().getPrice() * item.getQuantity())))
						.toList())
				.withTotal("Subtotal", new Money(subtotal));

		double amount = subtotal;

		if (!shippableItems.isEmpty()) {
			double shipping = calculateShipping(shippableItems);
			amount += shipping;
			paymentReceiptBuilder.withTotal("Shipping", new Money(shipping));
		}

		customer.withdraw(amount);

		paymentReceiptBuilder.withTotal("Amount", new Money(amount))
				.withTotal("Balance", new Money(customer.getBalance()));

		Receipt paymentReceipt = paymentReceiptBuilder.build();
		receipts.add(paymentReceipt);

		for (CartItem item : cart) {
			item.getProduct().reduceQuantity(item.getQuantity());
		}

		return receipts;
	}
}
