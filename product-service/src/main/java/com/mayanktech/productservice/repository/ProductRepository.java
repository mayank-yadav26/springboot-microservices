package com.mayanktech.productservice.repository;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.mayanktech.productservice.modal.Product;

public interface ProductRepository extends MongoRepository<Product, String>{

}
