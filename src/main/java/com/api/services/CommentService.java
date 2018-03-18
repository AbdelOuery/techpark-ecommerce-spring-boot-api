package com.api.services;

import org.springframework.stereotype.Service;

import com.api.JsonGenerator;
import com.api.entities.Comment;
import com.api.entities.Product;
import com.api.entities.User;
import com.api.repositories.CommentRepository;
import com.api.repositories.ProductRepository;
import com.api.repositories.UserRepository;

@Service
public class CommentService {
	private final CommentRepository commentRepository;
	private final ProductRepository productRepository;
    private final UserRepository userRepository;
    
    public CommentService(CommentRepository commentRepository,
						  ProductRepository productRepository,
						  UserRepository userRepository) {
    	this.commentRepository = commentRepository;
    	this.productRepository = productRepository;
    	this.userRepository = userRepository;
    }
	
	/** 
     * Recuperer tous les commentaires
     * 
     * @return Iterable<Comment>
     */
    public Iterable<Comment> getAll() {
        return commentRepository.findAll();
    }
    
    /** 
     * Recuperer tous les commentaires pour un produit
     * 
     * @param  id Long
     * @return Iterable<Comment>
     */
    public Iterable<Comment> getByProduct(Long id) {
    	Product product = productRepository.findOne(id);
        return product.getComments();
    }
    
    /** 
     * Recuperer tous les commentaires publie par un utilisateur
     * 
     * @param  id Long
     * @return Iterable<Comment>
     */
    public Iterable<Comment> getByUser(Long id) {
    	User user = userRepository.findOne(id);
        return user.getComments();
    }
    
    /**
     * Cree un commentaire
     * 
     * @param  comment Comment
     * @return String
     */
    public String create(Comment comment) {
    	
    	// Referencer le commentaire dans le produit
    	Product product = productRepository.findOne(comment.getProduct().getId());
    	product.addComment(comment);
    	
    	// Referencer le commentaire dans l'utilisateur
    	User user = userRepository.findOne(comment.getUser().getId());
    	user.addComment(comment);
    	
    	commentRepository.save(comment);
    	return JsonGenerator.buildJsonString("notice", "Comment added");
    }
    
    /**
     * Recuperer un commentaire
     * 
     * @param  id Long
     * @return Comment
     */
    public Comment getById(Long id) {
    	return commentRepository.findOne(id);
    }

    /**
     * Met a jour un commentaire
     * 
     * @param  id        Long
     * @param  newValues Comment
     * @return String
     */
    public String update(Long id, Comment newValues) {
    	Comment comment = commentRepository.findOne(id);
    	comment.setContent(newValues.getContent());
    	commentRepository.save(comment);
    	return JsonGenerator.buildJsonString("notice", "Comment updated");
    }

    /**
     * Supprimer un commentaire
     * 
     * @param  id Long
     * @return String
     */
    public String delete(Long id) {
    	commentRepository.delete(id);
    	return JsonGenerator.buildJsonString("notice", "Comment deleted");
    }
}
