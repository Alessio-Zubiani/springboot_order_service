package com.example.order.controller;

import com.example.order.dto.OrderRequest;
import com.example.order.service.OrderService;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;

@CrossOrigin(origins = "http://localhost:9000")
@RestController
@RequestMapping("/api/orders")
@RequiredArgsConstructor
public class OrderController {
	
	private final OrderService orderService;
	
	
	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public String placeOrder(@RequestBody OrderRequest orderRequest) {
		
		this.orderService.placeOrder(orderRequest);
		return "Order placed successfully";
	}

}
