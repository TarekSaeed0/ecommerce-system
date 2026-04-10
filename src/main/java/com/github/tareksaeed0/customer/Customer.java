package com.github.tareksaeed0.customer;

public class Customer {
	private String name;
	private double balance;

	public Customer(String name, double InitialBalance) {
		setName(name);
		setBalance(InitialBalance);
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		if (name == null || name.isBlank()) {
			throw new IllegalArgumentException("Name can't be null or empty.");
		}

		this.name = name;
	}

	public double getBalance() {
		return balance;
	}

	private void setBalance(double balance) {
		if (balance < 0) {
			throw new IllegalArgumentException("Balance can't be negative.");
		}

		this.balance = balance;
	}

	public void deposit(double amount) {
		if (amount < 0) {
			throw new IllegalArgumentException("Deposited amount can't be negative.");
		}

		this.balance += amount;
	}


	public void withdraw(double amount) {
		if (amount < 0) {
			throw new IllegalArgumentException("Withdrawn amount can't be negative.");
		}

		if (amount > balance) {
			throw new InsufficientBalanceException(balance, amount);
		}

		this.balance -= amount;
	}
}
