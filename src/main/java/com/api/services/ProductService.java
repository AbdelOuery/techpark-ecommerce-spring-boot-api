package com.api.services;

import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;

import com.api.JsonGenerator;
import com.api.entities.Category;
import com.api.entities.Product;
import com.api.repositories.CategoryRepository;
import com.api.repositories.ProductRepository;

@Service
public class ProductService {
	private final CategoryRepository categoryRepository;
	private final ProductRepository productRepository;
	
	public ProductService(CategoryRepository categoryRepository,
						  ProductRepository productRepository) {
		this.categoryRepository = categoryRepository;
		this.productRepository = productRepository;
	}
	
	/**
     * Recuperer tous les produits trie par le numero d'identifiant
     * 
     * @return Iterable<Product>
     */
    public Iterable<Product> getAll() {
        return productRepository.findAllByOrderByIdDesc();
    }
	
	/**
	 * Recuperer les derniers 9 produits trie par le numero d'identifiant
     * 
	 * @return Iterable<Product>
	 */
    public Iterable<Product> getLatest() {
        return productRepository.findTop9ByOrderByIdDesc();
    }
	
    /**
     * Recuperer tous les produits d'une categorie
     * 
     * @param  id Long
     * @return Iterable<Product>
     */
    public Iterable<Product> getByCategory(@PathVariable("id") Long id) {
    	Category category = categoryRepository.findOne(id);
        return category.getProducts();
    }
    
    /**
     * Cree un produit
     * 
     * @param  product Product
     * @return String
     */
    public String create(Product product) {
    	
    	// Ajouter le produit dans sa categorie
    	Category category = categoryRepository.findOne(product.getCategory().getId());
    	category.addProduct(product);
    	productRepository.save(product);
    	return JsonGenerator.buildJsonString("notice", "Product added");
    }

    /**
     * Recuperer un produit
     * 
     * @param  id Long
     * @return Product
     */
    public Product get(Long id) {
    	return productRepository.findOne(id);
    }
    
    /**
     * Met a jour un produit
     * 
     * @param  id        Long
     * @param  newValues Product
     * @return String
     */
    public String update(Long id, Product newValues) {
    	Product product = productRepository.findOne(id);
    	Category category = categoryRepository.findOne(newValues.getCategory().getId());
    	category.addProduct(product);
    	product.setTitle(newValues.getTitle());
    	product.setDescription(newValues.getDescription());
    	product.setPrice(newValues.getPrice());
    	product.setImgPath(newValues.getImgPath());
        productRepository.save(product);
        return JsonGenerator.buildJsonString("notice", "Product updated");
    }
   
    /**
     * Supprimer un produit
     * 
     * @param  id
     * @return String
     */
    public String delete(Long id) {
        productRepository.delete(id);
        return JsonGenerator.buildJsonString("notice", "Product deleted");
    }
}
