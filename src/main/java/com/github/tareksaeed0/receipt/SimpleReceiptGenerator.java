package com.github.tareksaeed0.receipt;

import java.util.ArrayList;
import java.util.List;
import com.github.tareksaeed0.checkout.CheckoutDetails;
import com.github.tareksaeed0.shipping.ShippableItem;
import com.github.tareksaeed0.shipping.ShippingDetails;
import com.github.tareksaeed0.units.Money;
import com.github.tareksaeed0.units.Weight;

public class SimpleReceiptGenerator implements ReceiptGenerator {
  private Receipt generateShippingReceipt(ShippingDetails details) {
    return new ReceiptBuilder().withTitle("Shipping Notice")
        .withItems(details.getShippables().stream().map(shippable -> {
          if (shippable instanceof ShippableItem item) {
            return new ReceiptItem(item.getName(), item.getQuantity(),
                new Weight(item.getWeight()));
          } else {
            return new ReceiptItem(shippable.getName(), 1,
                new Weight(shippable.getWeight()));
          }
        }).toList()).withTotal("Total Package Weight",
            new Weight(details.getTotalPackageWeight()))
        .build();
  }

  private Receipt generatePaymentReceipt(CheckoutDetails details) {
    ReceiptBuilder paymentReceiptBuilder = new ReceiptBuilder()
        .withTitle("Checkout Receipt")
        .withItems(details.getItems().stream()
            .map(item -> new ReceiptItem(item.getProduct().getName(),
                item.getQuantity(),
                new Money(item.getProduct().getPrice() * item.getQuantity())))
            .toList())
        .withTotal("Subtotal", new Money(details.getSubtotal()));

    if (details.getShippingDetails() != null) {
      paymentReceiptBuilder.withTotal("Shipping Cost",
          new Money(details.getShippingDetails().getShippingCost()));
    }

    paymentReceiptBuilder
        .withTotal("Paid Amount", new Money(details.getPaidAmount())).withTotal(
            "Remaining Balance", new Money(details.getRemainingBalance()));

    return paymentReceiptBuilder.build();
  }

  @Override
  public List<Receipt> generateReceipts(CheckoutDetails details) {
    List<Receipt> receipts = new ArrayList<>();

    if (details.getShippingDetails() != null) {
      Receipt shippingReceipt =
          generateShippingReceipt(details.getShippingDetails());

      receipts.add(shippingReceipt);
    }

    Receipt paymentReceipt = generatePaymentReceipt(details);
    receipts.add(paymentReceipt);

    return receipts;
  }
}
