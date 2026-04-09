package com.github.tareksaeed0.units;

import java.text.DecimalFormat;

public class Money {
  private final double value;

  public Money(double value) {
    if (value < 0) {
      throw new IllegalArgumentException("Money can't be negative.");
    }

    this.value = value;
  }

  public double getValue() {
    return value;
  }

  @Override
  public String toString() {
    DecimalFormat decimalFormat = new DecimalFormat("###,###,###.##");
    return "$" + decimalFormat.format(value);
  }
}
