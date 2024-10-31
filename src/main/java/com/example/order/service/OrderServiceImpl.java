package com.example.order.service;

import java.util.UUID;

import com.example.order.client.InventoryServiceClient;
import com.example.order.dto.OrderRequest;
import com.example.order.model.Order;
import com.example.order.repository.OrderRepository;

import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService {

	private final OrderRepository orderRepository;
	private final InventoryServiceClient inventoryServiceClient;
	
	
	@Override
	public void placeOrder(OrderRequest orderRequest) {
		
		boolean isInStock = this.inventoryServiceClient.isInStock(orderRequest.skuCode(), orderRequest.quantity());
		if(isInStock) {
			Order order = Order.builder()
					.orderNumber(UUID.randomUUID().toString())
					.price(orderRequest.price())
					.skuCode(orderRequest.skuCode())
					.quantity(orderRequest.quantity())
					.build();
			
			this.orderRepository.save(order);
		} else {
			throw new RuntimeException("Product with SkuCode [".concat(orderRequest.skuCode()).concat("] is not in stock"));
		}
	}

}
