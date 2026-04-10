package com.github.tareksaeed0.customer;

import com.github.tareksaeed0.units.Money;

public class InsufficientBalanceException extends RuntimeException {
  private final double balance;
  private final double requiredAmount;

  public InsufficientBalanceException(double balance, double requiredAmount) {
    super("Insufficient balance. Current balance: " + new Money(balance)
        + ", required amount: " + new Money(requiredAmount) + ".");

    this.balance = balance;
    this.requiredAmount = requiredAmount;
  }

  public double getBalance() {
    return balance;
  }

  public double getRequiredAmount() {
    return requiredAmount;
  }
}
