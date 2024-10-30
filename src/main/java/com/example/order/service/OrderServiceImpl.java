package com.example.order.service;

import java.util.UUID;

import com.example.order.dto.OrderRequest;
import com.example.order.model.Order;
import com.example.order.repository.OrderRepository;

import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService {

	private final OrderRepository orderRepository;
	
	
	@Override
	public void placeOrder(OrderRequest orderRequest) {
		
		Order order = Order.builder()
				.orderNumber(UUID.randomUUID().toString())
				.price(orderRequest.price())
				.skuCode(orderRequest.skuCode())
				.quantity(orderRequest.quantity())
				.build();
		
		this.orderRepository.save(order);
	}

}
