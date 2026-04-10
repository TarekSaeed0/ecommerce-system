package com.github.tareksaeed0.receipt;

import java.util.Collections;
import java.util.List;
import java.util.Objects;

public class Receipt {
	private final String title;
	private final List<ReceiptItem> items;
	private final List<ReceiptTotal> totals;

	public Receipt(String title, List<ReceiptItem> items,
			List<ReceiptTotal> totals) {
		this.title = Objects.requireNonNull(title, "Title can't be null.");
		this.items =
				List.copyOf(Objects.requireNonNull(items, "Items can't be null."));
		this.totals =
				List.copyOf(Objects.requireNonNull(totals, "Totals can't be null."));
	}

	public String getTitle() {
		return title;
	}

	public List<ReceiptItem> getItems() {
		return Collections.unmodifiableList(items);
	}

	public List<ReceiptTotal> getTotals() {
		return Collections.unmodifiableList(totals);
	}
}
