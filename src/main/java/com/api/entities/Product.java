package com.api.entities;

import java.io.Serializable;
import java.util.Date;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@Entity
@EntityListeners(AuditingEntityListener.class)
@JsonIgnoreProperties(value = "dateAdded", allowGetters=true)
public class Product implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	
	@ManyToOne
	private Category category;
	
	@OneToMany(cascade = CascadeType.ALL, mappedBy = "product", orphanRemoval = true)
	private Set<Purchase> purchases;
	
	@OneToMany(cascade = CascadeType.ALL, mappedBy = "product", orphanRemoval = true)
	private Set<Comment> comments;
	
	@NotNull
	private String title;
	
	@NotNull
	private String description;
	
	@NotNull
	private double price;
	
	@Column(nullable = false, updatable = false)
	@Temporal(TemporalType.DATE)
	@CreatedDate
	private Date dateAdded;
	
	private String imgPath;
	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}
	
	public Category getCategory() {
		return category;
	}
	
	public void setCategory(Category category) {
		this.category = category;
	}

	@JsonIgnore
	public Set<Purchase> getPurchases() {
		return purchases;
	}
	
	@JsonProperty
	public void setPurchases(Set<Purchase> purchases) {
		this.purchases = purchases;
	}
	
	/**
	 * Ajouter un achat dans las liste des achats
     * 
     * @param purchase Purchase
	 */
	public void addPurchase(Purchase purchase) {
		this.purchases.add(purchase);
		if(purchase.getProduct() != this) {
			purchase.setProduct(this);
		}
	}

	public Set<Comment> getComments() {
		return comments;
	}

	public void setComments(Set<Comment> comments) {
		this.comments = comments;
	}
	
	/**
	 * Ajouter un commentaire dans la liste des achats
     * 
     * @param comment Comment
	 */
	public void addComment(Comment comment) {
		this.comments.add(comment);
		if(comment.getProduct() != this) {
			comment.setProduct(this);
		}
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public double getPrice() {
		return price;
	}

	public void setPrice(double price) {
		this.price = price;
	}

	public Date getDateAdded() {
		return dateAdded;
	}

	public void setDateAdded(Date dateAdded) {
		this.dateAdded = dateAdded;
	}

	public String getImgPath() {
		return imgPath;
	}

	public void setImgPath(String imgPath) {
		this.imgPath = imgPath;
	}

	@Override
	public String toString() {
		return "Product [id=" + id + ", category=" + category + ", category=[" + category + "], purchases="
				+ purchases + ", comments=" + comments + ", title=" + title + ", description=" + description
				+ ", price=" + price + ", dateAdded=" + dateAdded + ", imgPath=" + imgPath + "]";
	}
}
