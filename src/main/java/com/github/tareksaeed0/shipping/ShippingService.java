package com.github.tareksaeed0.shipping;

import java.util.List;

public interface ShippingService {
	ShippingDetails ship(List<Shippable> shippables);
}
