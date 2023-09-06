package com.mayanktech.orderservice.service;

import java.util.Arrays;
import java.util.List;
import java.util.UUID;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.reactive.function.client.WebClient;

import com.mayanktech.orderservice.dto.InventoryResponse;
import com.mayanktech.orderservice.dto.OrderLineItemsDto;
import com.mayanktech.orderservice.dto.OrderRequest;
import com.mayanktech.orderservice.modal.Order;
import com.mayanktech.orderservice.modal.OrderLineItems;
import com.mayanktech.orderservice.repository.OrderRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional
public class OrderService {

	private final OrderRepository orderRepository;
	private final WebClient webClient;

	public void placeOrder(OrderRequest orderRequest) {
		Order order = new Order();
		order.setOrderNumber(UUID.randomUUID().toString());

		List<OrderLineItems> orderLineItems = orderRequest.getOrderLineItemsDtoList().stream().map(this::mapToDto)
				.toList();

		order.setOrderLineItemsList(orderLineItems);

		List<String> skuCodesList = order.getOrderLineItemsList().stream().map(OrderLineItems::getSkuCode).toList();

		// call inventory service ,and place order if product is in stock
		InventoryResponse[] inventoryResponseArray = webClient.get()
				.uri("http://localhost:8082/api/inventory",
						uriBuilder -> uriBuilder.queryParam("skuCodeList", skuCodesList).build())
				.retrieve().bodyToMono(InventoryResponse[].class).block();

		boolean allProductsInStock = Arrays.stream(inventoryResponseArray)
				.allMatch(inventoryResponse -> inventoryResponse.getIsInStock());

		if (allProductsInStock) {
			orderRepository.save(order);
		} else {
			throw new IllegalArgumentException("Product is not in stock, please try again later.");
		}
	}

	private OrderLineItems mapToDto(OrderLineItemsDto orderLineItemsDto) {
		OrderLineItems orderLineItems = new OrderLineItems();
		orderLineItems.setPrice(orderLineItemsDto.getPrice());
		orderLineItems.setQuantity(orderLineItemsDto.getQuantity());
		orderLineItems.setSkuCode(orderLineItemsDto.getSkuCode());
		return orderLineItems;
	}

}
