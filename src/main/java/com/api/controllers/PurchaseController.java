package com.api.controllers;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.api.entities.Purchase;
import com.api.services.PurchaseService;

@RestController
@RequestMapping("/api/purchase")
public class PurchaseController {
	private final PurchaseService purchaseService;
	
    public PurchaseController(PurchaseService purchaseService) {
    	this.purchaseService = purchaseService;
    }

    @GetMapping("/all")
    public Iterable<Purchase> getAll() {
        return purchaseService.getAll();
    }
    
    @GetMapping("/product/{id}")
    public Iterable<Purchase> getByProduct(@PathVariable("id") Long id) {
    	return purchaseService.getByProduct(id);
    }

    @GetMapping("/user/{id}")
    public Iterable<Purchase> getByUser(@PathVariable("id") Long id) {
    	return purchaseService.getByUser(id);
    }

    @PostMapping("/create")
    public String create(@RequestBody Purchase purchase) {
    	return purchaseService.create(purchase);
    }

    @GetMapping("/{id}")
    public Purchase read(@PathVariable("id") Long id) {
    	return purchaseService.get(id);
    }

    @DeleteMapping("/delete/{id}")
    public String delete(@PathVariable("id") Long id) {
    	return purchaseService.delete(id);
    }
}
