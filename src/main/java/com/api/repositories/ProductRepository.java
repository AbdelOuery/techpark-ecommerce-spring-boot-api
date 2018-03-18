package com.api.repositories;

import org.springframework.data.repository.CrudRepository;

import com.api.entities.Product;

public interface ProductRepository extends CrudRepository<Product, Long> {
	Iterable<Product> findAllByOrderByIdDesc();
	Iterable<Product> findTop9ByOrderByIdDesc();
}
