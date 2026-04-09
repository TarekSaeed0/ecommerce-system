package com.github.tareksaeed0.product;

public interface Product {
  String getName();

  double getPrice();

  int getQuantity();

  void reduceQuantity(int amount);

  public <T extends ProductInformation> T getInformation(Class<T> type);

  public <T extends ProductInformation> boolean hasInformation(Class<T> type);
}
