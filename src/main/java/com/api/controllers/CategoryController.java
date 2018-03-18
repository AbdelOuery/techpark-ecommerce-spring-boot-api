package com.api.controllers;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.api.entities.Category;
import com.api.services.CategoryService;


@RestController
@RequestMapping("/api/category")
public class CategoryController {
	private final CategoryService categoryService;
	
	public CategoryController(CategoryService categoryService) {
		this.categoryService = categoryService;
	}
	
    @GetMapping("/all")
    public Iterable<Category> getAll() {
        return categoryService.getAll();
    }

    @GetMapping("/{id}")
    public Category get(@PathVariable("id") Long id) {
    	return categoryService.getById(id);
    }
    
    @PostMapping("/create")
    public String create(@RequestBody Category category) {
    	return categoryService.create(category);
    }

	@PutMapping("/update/{id}")
    public String update(@PathVariable("id") Long id, @RequestBody Category newValues) {
		return categoryService.update(id, newValues);
    }

    @DeleteMapping("/delete/{id}")
	public String delete(@PathVariable("id") Long id) {
    	return categoryService.delete(id);
    }
}
