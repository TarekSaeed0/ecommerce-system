package com.github.tareksaeed0.checkout;

import com.github.tareksaeed0.cart.Cart;
import com.github.tareksaeed0.customer.Customer;

public interface CheckoutService {
	CheckoutDetails checkout(Customer customer, Cart cart);
}
