package com.mayanktech.inventoryservice.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.mayanktech.inventoryservice.modal.Inventory;

public interface InventoryRepository extends JpaRepository<Inventory,Long>{

	Optional<Inventory> findBySkuCode(String skuCode);

}
