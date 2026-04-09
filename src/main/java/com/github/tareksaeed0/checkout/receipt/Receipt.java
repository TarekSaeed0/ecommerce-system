package com.github.tareksaeed0.checkout.receipt;

import java.util.List;

public class Receipt {
	public static class Item {
		private final String name;
		private final int quantity;
		private final Object value;

		public Item(String name, int quantity, Object value) {
			this.name = name;
			this.quantity = quantity;
			this.value = value;
		}

		public String getName() {
			return name;
		}

		public int getQuantity() {
			return quantity;
		}

		public Object getValue() {
			return value;
		}
	}

	public static class Total {
		private final String name;
		private final Object value;

		public Total(String name, Object value) {
			this.name = name;
			this.value = value;
		}

		public Object getValue() {
			return value;
		}

		public String getName() {
			return name;
		}
	}

	private final String title;
	private final List<Item> items;
	private final List<Total> totals;

	public Receipt(String title, List<Item> items, List<Total> totals) {
		this.title = title;
		this.items = items;
		this.totals = totals;
	}

	public String getTitle() {
		return title;
	}

	public List<Item> getItems() {
		return items;
	}

	public List<Total> getTotals() {
		return totals;
	}
}
