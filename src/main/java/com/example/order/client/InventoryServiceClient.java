package com.example.order.client;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.service.annotation.GetExchange;

import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import io.github.resilience4j.retry.annotation.Retry;

public interface InventoryServiceClient {
	
	Logger log = LoggerFactory.getLogger(InventoryServiceClient.class);
	
	@GetExchange("/api/inventory")
	@CircuitBreaker(name = "inventory", fallbackMethod = "fallbackMethod")
	@Retry(name = "inventory")
	boolean isInStock(@RequestParam("skuCode") String skuCode, @RequestParam("quantity") Integer quantity);
	
	default boolean fallbackMethod(String skuCode, Integer quantity, Throwable throwable) {
		log.warn("Cannot get inventory for skuCode [{}]. Failure reason: {}", skuCode, throwable.getMessage());
		return false;
	}

}
