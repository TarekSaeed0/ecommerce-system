package com.github.tareksaeed0.checkout;

import java.util.List;
import com.github.tareksaeed0.cart.Cart;
import com.github.tareksaeed0.checkout.receipt.Receipt;
import com.github.tareksaeed0.customer.Customer;

public interface CheckoutService {
	List<Receipt> checkout(Customer customer, Cart cart);
}
