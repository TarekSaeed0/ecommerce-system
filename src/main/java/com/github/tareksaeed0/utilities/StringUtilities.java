package com.github.tareksaeed0.utilities;

public class StringUtilities {
  public static String leftPad(String value, int length) {
    return String.format("%" + length + "s", value);
  }

  public static String rightPad(String value, int length) {
    return String.format("%-" + length + "s", value);
  }

  public static String centerPad(String value, int length) {
    int leftPadding = (length - value.length()) / 2;
    int rightPadding = length - value.length() - leftPadding;
    return " ".repeat(leftPadding) + value + " ".repeat(rightPadding);
  }
}
