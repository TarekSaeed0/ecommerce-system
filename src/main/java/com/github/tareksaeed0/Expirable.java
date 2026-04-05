package com.github.tareksaeed0;

import java.time.LocalDateTime;

public interface Expirable {
	LocalDateTime getExpirationTime();

	default boolean isExpired() {
		return LocalDateTime.now().isAfter(getExpirationTime());
	}
}
