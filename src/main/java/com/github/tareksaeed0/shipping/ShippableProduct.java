package com.github.tareksaeed0.shipping;

import com.github.tareksaeed0.product.Product;
import com.github.tareksaeed0.product.ProductWithInformation;

public class ShippableProduct
    extends ProductWithInformation<ShippingInformation> implements Shippable {
  public ShippableProduct(Product product) {
    super(product, ShippingInformation.class);
  }

  @Override
  public double getWeight() {
    return getInformation(ShippingInformation.class).getWeight();
  }
}
