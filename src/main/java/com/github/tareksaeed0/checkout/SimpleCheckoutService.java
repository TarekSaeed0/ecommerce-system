package com.github.tareksaeed0.checkout;

import java.util.List;
import com.github.tareksaeed0.cart.Cart;
import com.github.tareksaeed0.cart.EmptyCartException;
import com.github.tareksaeed0.checkout.receipt.Receipt;
import com.github.tareksaeed0.customer.Customer;
import com.github.tareksaeed0.expiration.ExpirableProduct;
import com.github.tareksaeed0.expiration.ExpirationInformation;
import com.github.tareksaeed0.expiration.ExpiredProductsException;
import com.github.tareksaeed0.product.OutOfStockProductsException;
import com.github.tareksaeed0.product.Product;
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

	private List<ExpirableProduct> getExpiredProducts(Cart cart) {
		return cart.stream()
				.filter(item -> item.getProduct()
						.hasInformation(ExpirationInformation.class))
				.map(item -> new ExpirableProduct(item.getProduct()))
				.filter(ExpirableProduct::isExpired).toList();
	}

	private List<Product> getOutOfStockProducts(Cart cart) {
		return cart.stream()
				.filter(item -> item.getProduct().getQuantity() < item.getQuantity())
				.map(Cart.Item::getProduct).toList();
	}

	private void validateCart(Cart cart) {
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
		validateCart(cart);

		List<ShippableItem> shippableItems = getShippableItems(cart);

		double totalPackageWeight =
				shippableItems.stream().mapToDouble(Shippable::getWeight).sum();

		Receipt shippingReceipt = new Receipt("Shipping Notice",
				shippableItems.stream()
						.map(item -> new Receipt.Item(item.getName(), item.getQuantity(),
								new Weight(item.getWeight())))
						.toList(),
				List.of(new Receipt.Total("Total Package Weight",
						new Weight(totalPackageWeight))));

		double subtotal = calculateSubtotal(cart);
		double shipping = calculateShipping(shippableItems);
		double amount = subtotal + shipping;

		customer.withdraw(amount);

		double balance = customer.getBalance();

		Receipt paymentReceipt = new Receipt("Checkout Receipt",
				cart.stream()
						.map(item -> new Receipt.Item(item.getProduct().getName(),
								item.getQuantity(),
								new Money(item.getProduct().getPrice() * item.getQuantity())))
						.toList(),
				List.of(new Receipt.Total("Subtotal", new Money(subtotal)),
						new Receipt.Total("Shipping", new Money(shipping)),
						new Receipt.Total("Amount", new Money(amount)),
						new Receipt.Total("Balance", new Money(balance))));

		return List.of(shippingReceipt, paymentReceipt);
	}
}
