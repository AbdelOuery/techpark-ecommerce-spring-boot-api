package com.api.services;

import org.springframework.stereotype.Service;

import com.api.JsonGenerator;
import com.api.entities.Product;
import com.api.entities.Purchase;
import com.api.entities.User;
import com.api.repositories.ProductRepository;
import com.api.repositories.PurchaseRepository;
import com.api.repositories.UserRepository;

@Service
public class PurchaseService {
	private final PurchaseRepository purchaseRepository;
	private final ProductRepository productRepository;
    private final UserRepository userRepository;
    
    public PurchaseService(PurchaseRepository purchaseRepository,
						   ProductRepository productRepository,
						   UserRepository userRepository) {
    	this.purchaseRepository = purchaseRepository;
    	this.productRepository = productRepository;
    	this.userRepository = userRepository;
    }
    
    /**
     * Recuperer tous les achats
     * 
     * @return Iterable<Purchase>
     */
    public Iterable<Purchase> getAll() {
        return purchaseRepository.findAll();
    }
    
    /**
     * Recuperer tous les achats d'un produit
     * 
     * @param  id Long
     * @return Iterable<Purchase>
     */
    public Iterable<Purchase> getByProduct(Long id) {
    	Product product = productRepository.findOne(id);
        return product.getPurchases();
    }
    
    /**
     * Recuperer tous les achats d'un utilisateur
     * 
     * @param  id Long
     * @return Iterable<Purchase>
     */
    public Iterable<Purchase> getByUser(Long id) {
    	User user = userRepository.findOne(id);
        return user.getPurchases();
    }


    /**
     * Cree un achat
     * 
     * @param  purchase purchase
     * @return String
     */
    public String create(Purchase purchase) {
    	
    	// Referencer l'achat dans le produit
    	Product product = productRepository.findOne(purchase.getProduct().getId());
    	product.addPurchase(purchase);
    	
    	// References l'achat dans l'utilisateur
    	User user = userRepository.findOne(purchase.getUser().getId());
    	user.addPurchase(purchase);
    	
    	purchaseRepository.save(purchase);
    	return JsonGenerator.buildJsonString("notice", "Comment added");
    }

    /**
     * Recuperer un achat
     * 
     * @param  id Long
     * @return Purchase
     */
    public Purchase get(Long id) {
    	return purchaseRepository.findOne(id);
    }

    /**
     * Supprimer un achat
     * 
     * @param  id Long
     * @return String
     */
    public String delete(Long id) {
        purchaseRepository.delete(id);
        return JsonGenerator.buildJsonString("notice", "Comment deleted");
    }
}
