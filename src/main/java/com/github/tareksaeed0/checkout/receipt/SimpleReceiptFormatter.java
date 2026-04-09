package com.github.tareksaeed0.checkout.receipt;

import java.util.stream.Stream;

public class SimpleReceiptFormatter implements ReceiptFormatter {
	String leftPad(String value, int length) {
		return String.format("%" + length + "s", value);
	}

	String rightPad(String value, int length) {
		return String.format("%-" + length + "s", value);
	}

	String centerPad(String value, int length) {
		int leftPadding = (length - value.length()) / 2;
		int rightPadding = length - value.length() - leftPadding;
		return " ".repeat(leftPadding) + value + " ".repeat(rightPadding);
	}

	public String format(Receipt receipt) {
		StringBuilder builder = new StringBuilder();

		int maximumQuantityLength = receipt.getItems().stream()
				.map(item -> String.valueOf(item.getQuantity()).length())
				.max(Integer::compareTo).orElse(0);

		int maximumNameLength = Stream
				.concat(receipt.getItems().stream().map(Receipt.Item::getName),
						receipt.getTotals().stream().map(Receipt.Total::getName))
				.map(item -> item.length()).max(Integer::compareTo).orElse(0);

		int maximumValueLength = Stream
				.concat(
						receipt.getItems().stream().map(item -> item.getValue().toString()),
						receipt.getTotals().stream()
								.map(total -> total.getValue().toString()))
				.map(item -> item.length()).max(Integer::compareTo).orElse(0);

		int totalWidth =
				maximumQuantityLength + 2 + maximumNameLength + 1 + maximumValueLength;

		builder.append("** ").append(centerPad(receipt.getTitle(), totalWidth - 6))
				.append(" **\n");

		for (Receipt.Item item : receipt.getItems()) {
			builder
					.append(leftPad(String.valueOf(item.getQuantity()),
							maximumQuantityLength))
					.append("x ").append(rightPad(item.getName(), maximumNameLength))
					.append(" ")
					.append(leftPad(item.getValue().toString(), maximumValueLength))
					.append("\n");
		}

		builder.append("-".repeat(totalWidth)).append("\n");

		for (Receipt.Total total : receipt.getTotals()) {
			builder
					.append(rightPad(total.getName(),
							maximumQuantityLength + 2 + maximumNameLength))
					.append(" ")
					.append(leftPad(total.getValue().toString(), maximumValueLength))
					.append("\n");
		}

		return builder.toString();
	}
}
