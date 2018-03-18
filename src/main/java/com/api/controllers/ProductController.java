package com.api.controllers;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.api.entities.Product;
import com.api.services.ProductService;


@RestController
@RequestMapping("/api/product")
public class ProductController {
	private final ProductService productService;
	
	public ProductController(ProductService productService) {
		this.productService = productService;
	}

    @GetMapping("/all")
    public Iterable<Product> fetchAll() {
		return productService.getAll();
    }

    @GetMapping("/latest")
    public Iterable<Product> fetchLatest() {
		return productService.getLatest();
    }

    @GetMapping("/category/{id}")
    public Iterable<Product> getByCategory(@PathVariable("id") Long id) {
    	return productService.getByCategory(id);
    }
    
    @PostMapping("/create")
    public String create(@RequestBody Product product) {
    	return productService.create(product);
    }

    @GetMapping("/{id}")
    public Product read(@PathVariable("id") Long id) {
    	return productService.get(id);
    }

    @PutMapping("/update/{id}")
    public String update(@PathVariable("id") Long id, @RequestBody Product newValues) {
    	return productService.update(id, newValues);
    }
   
    @DeleteMapping("/delete/{id}")
    public String delete(@PathVariable("id") Long id) {
        return productService.delete(id);
    }
}
