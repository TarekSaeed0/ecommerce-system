package com.github.tareksaeed0.shipping;

import java.util.List;

public interface ShippingService {
	double ship(List<Shippable> items);
}
