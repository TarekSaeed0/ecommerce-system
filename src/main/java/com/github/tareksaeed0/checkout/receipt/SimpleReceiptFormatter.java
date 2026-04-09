package com.github.tareksaeed0.checkout.receipt;

import java.util.stream.Stream;
import com.github.tareksaeed0.utilities.StringUtilities;

public class SimpleReceiptFormatter implements ReceiptFormatter {
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

		builder.append("** ")
				.append(StringUtilities.centerPad(receipt.getTitle(), totalWidth - 6))
				.append(" **\n");

		for (Receipt.Item item : receipt.getItems()) {
			builder
					.append(StringUtilities.leftPad(String.valueOf(item.getQuantity()),
							maximumQuantityLength))
					.append("x ")
					.append(StringUtilities.rightPad(item.getName(), maximumNameLength))
					.append(" ").append(StringUtilities
							.leftPad(item.getValue().toString(), maximumValueLength))
					.append("\n");
		}

		builder.append("-".repeat(totalWidth)).append("\n");

		for (Receipt.Total total : receipt.getTotals()) {
			builder
					.append(StringUtilities.rightPad(total.getName(),
							maximumQuantityLength + 2 + maximumNameLength))
					.append(" ").append(StringUtilities
							.leftPad(total.getValue().toString(), maximumValueLength))
					.append("\n");
		}

		return builder.toString();
	}
}
