package com.api.repositories;

import org.springframework.data.repository.CrudRepository;

import com.api.entities.Category;

public interface CategoryRepository extends CrudRepository<Category, Long> { }
