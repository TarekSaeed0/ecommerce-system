package com.github.tareksaeed0.receipt;

import java.util.List;
import com.github.tareksaeed0.checkout.CheckoutDetails;

public interface ReceiptGenerator {
  List<Receipt> generateReceipts(CheckoutDetails details);
}
