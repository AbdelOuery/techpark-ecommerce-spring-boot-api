package com.api.entities;

import java.io.Serializable;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

@Entity
public class Category implements Serializable {

	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	
	@OneToMany(cascade = CascadeType.ALL, mappedBy = "category", orphanRemoval = true)
	private Set<Product> products;
	
	@NotNull
	private String title;
	
	public Long getId() {
		return id;
	}
	
	public void setId(Long id) {
		this.id = id;
	}
	
	@JsonIgnore
	public Set<Product> getProducts() {
		return products;
	}
	
	@JsonProperty
	public void setProducts(Set<Product> products) {
		this.products = products;
	}
	
	/**
	 * Ajouter un produit dans la liste des produits
     *
	 * @param product Product
	 */
	public void addProduct(Product product) {
		this.products.add(product);
		if(product.getCategory() != this) {
			product.setCategory(this);
		}
	}

	public String getTitle() {
		return title;
	}
	
	public void setTitle(String title) {
		this.title = title;
	}

	@Override
	public String toString() {
		return "Category [id=" + id + ", products=" + products + ", title=" + title + "]";
	}
}
