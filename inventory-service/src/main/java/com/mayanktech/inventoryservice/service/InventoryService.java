package com.mayanktech.inventoryservice.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.mayanktech.inventoryservice.dto.InventoryResponse;
import com.mayanktech.inventoryservice.repository.InventoryRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class InventoryService {
	
	private final InventoryRepository inventoryRepository;
	
	@Transactional(readOnly=true)
	public List<InventoryResponse> isInStock(List<String> skuCodeList) {
		return inventoryRepository.findBySkuCodeIn(skuCodeList).stream()
				.map(inventory -> 
					InventoryResponse.builder()
					.skuCode(inventory.getSkuCode())
					.isInStock(inventory.getQuantity()>0?true:false)
					.build()
				).toList();
	}
}
