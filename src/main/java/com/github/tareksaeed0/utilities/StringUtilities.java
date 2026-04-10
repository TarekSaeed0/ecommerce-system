package com.github.tareksaeed0.utilities;

import java.util.Objects;

public class StringUtilities {
  public static String leftPad(String value, int length) {
    Objects.requireNonNull(value, "Value can't be null.");

    if (length < 0) {
      throw new IllegalArgumentException("Length can't be negative.");
    }

    return String.format("%" + length + "s", value);
  }

  public static String rightPad(String value, int length) {
    Objects.requireNonNull(value, "Value can't be null.");

    if (length < 0) {
      throw new IllegalArgumentException("Length can't be negative.");
    }

    return String.format("%-" + length + "s", value);
  }

  public static String centerPad(String value, int length) {
    Objects.requireNonNull(value, "Value can't be null.");

    if (length < 0) {
      throw new IllegalArgumentException("Length can't be negative.");
    }

    int leftPadding = (length - value.length()) / 2;
    int rightPadding = length - value.length() - leftPadding;
    return " ".repeat(leftPadding) + value + " ".repeat(rightPadding);
  }
}
