package com.api.repositories;

import org.springframework.data.repository.CrudRepository;

import com.api.entities.Purchase;

public interface PurchaseRepository extends CrudRepository<Purchase, Long> { }
