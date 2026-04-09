package com.github.tareksaeed0.units;

import java.text.DecimalFormat;
import java.util.List;

public class Weight {
	private final double value;

	public Weight(double value) {
		if (value < 0) {
			throw new IllegalArgumentException("Weight can't be negative.");
		}

		this.value = value;
	}

	public double getValue() {
		return value;
	}

	@Override
	public String toString() {
		List<String> units = List.of("mg", "g", "kg");
		List<Double> thresholds = List.of(1e-9, 1e-3, 1.0);

		int i = 0;
		while (i < thresholds.size() - 1) {
			if (value < thresholds.get(i + 1)) {
				break;
			}
			i++;
		}

		DecimalFormat decimalFormat = new DecimalFormat("#.##");
		return decimalFormat.format(value / thresholds.get(i)) + units.get(i);
	}
}
