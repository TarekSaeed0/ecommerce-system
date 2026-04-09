package com.github.tareksaeed0.customer;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

public class CustomerTest {
	@Test
	public void cantCreateCustomerWithNullOrBlankName() {
		assertThrows(IllegalArgumentException.class, () -> new Customer(null, 100));
		assertThrows(IllegalArgumentException.class, () -> new Customer("", 100));
		assertThrows(IllegalArgumentException.class,
				() -> new Customer("   ", 100));
	}

	@Test
	public void cantCreateCustomerWithNegativeBalance() {
		assertThrows(IllegalArgumentException.class,
				() -> new Customer("Tarek", -100));
		assertThrows(IllegalArgumentException.class,
				() -> new Customer("Mohamed", -1));
		assertThrows(IllegalArgumentException.class,
				() -> new Customer("Ahmed", -0.1));
	}

	@Test
	public void cantDepositNegativeAmount() {
		Customer customer = new Customer("Tarek", 100);

		assertThrows(IllegalArgumentException.class, () -> customer.deposit(-100));
		assertThrows(IllegalArgumentException.class, () -> customer.deposit(-1));
	}

	@Test
	public void cantWithdrawNegativeAmount() {
		Customer customer = new Customer("Tarek", 100);

		assertThrows(IllegalArgumentException.class, () -> customer.withdraw(-100));
		assertThrows(IllegalArgumentException.class, () -> customer.withdraw(-1));
	}

	@Test
	public void cantWithdrawAmountGreaterThanBalance() {
		Customer customer = new Customer("Tarek", 100);

		assertThrows(IllegalArgumentException.class, () -> customer.withdraw(101));
		assertThrows(IllegalArgumentException.class, () -> customer.withdraw(1000));
	}

	@Test
	public void canDepositAndWithdraw() {
		Customer customer = new Customer("Tarek", 100);

		customer.deposit(50);
		assertTrue(customer.getBalance() == 150);

		customer.withdraw(20);
		assertTrue(customer.getBalance() == 130);
	}
}
