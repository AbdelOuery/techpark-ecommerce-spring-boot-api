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
public class User implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
    @GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;

	@OneToMany(cascade = CascadeType.ALL, mappedBy = "user", orphanRemoval = true)
	private Set<Purchase> purchases;

	@OneToMany(cascade = CascadeType.ALL, mappedBy = "user", orphanRemoval = true)
	private Set<Comment> comments;

	@NotNull
    private String email;

	@NotNull
    private String password;

	@NotNull
    private String lastName;

	@NotNull
    private String firstName;

	@Temporal(TemporalType.DATE)
	@NotNull
    private Date birthDate;

	@Column(nullable = false, updatable = false)
	@Temporal(TemporalType.DATE)
	@CreatedDate
	private Date dateAdded;

    private Integer admin;

    public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
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
	 * Ajouter un achat dans la liste des achats
     *
	 * @param purchase Purchase
	 */
	public void addPurchase(Purchase purchase) {
		this.purchases.add(purchase);
		if(purchase.getUser() != this) {
			purchase.setUser(this);
		}
	}

    @JsonIgnore
	public Set<Comment> getComments() {
		return comments;
	}

    @JsonProperty
	public void setComments(Set<Comment> comments) {
		this.comments = comments;
	}

	/**
	 * Ajouter un commentaire dans la liste des achats
     *
	 * @param purchase Purchase
	 */
	public void addComment(Comment comment) {
		this.comments.add(comment);
		if(comment.getUser() != this) {
			comment.setUser(this);
		}
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public Date getBirthDate() {
		return birthDate;
	}

	public void setBirthDate(Date date) {
		this.birthDate = date;
	}

	public Date getDateAdded() {
		return dateAdded;
	}

	public void setDateAdded(Date dateAdded) {
		this.dateAdded = dateAdded;
	}

	public Integer getAdmin() {
		return admin;
	}

	public void setAdmin(Integer admin) {
		this.admin = admin;
	}

	@Override
	public String toString() {
		return "User [id=" + id + ", email=" + email + ", password=" + password + ", lastName=" + lastName
				+ ", firstName=" + firstName + ", birthDate=" + birthDate + ", dateAdded=" + dateAdded
				+ ", admin=" + admin + "]";
	}
}
