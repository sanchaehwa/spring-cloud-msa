package com.example.userservice.client;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.example.userservice.vo.ResponseOrder;

public interface OrderServiceClient {

	@GetMapping("/order-service/{userId}/orders")
	List<ResponseOrder> getOrders(@PathVariable String userId);
}
