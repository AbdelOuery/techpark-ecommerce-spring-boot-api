package com.api.services;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.api.JsonGenerator;
import com.api.entities.User;
import com.api.repositories.UserRepository;

@Service
public class UserService {
	private final UserRepository userRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;
    
    public UserService(UserRepository userRepository,
					   BCryptPasswordEncoder bCryptPasswordEncoder) {
    	this.userRepository = userRepository;
    	this.bCryptPasswordEncoder = bCryptPasswordEncoder;
    }
    
    /** 
     * Recuperer tous les utilisateurs
     * 
     * @return Iterable<User>
     */
    public Iterable<User> getAll() {
    	return userRepository.findAll();
    }
    
    /**
     * Recuperer un utilisateur par email
     * 
     * @param  email String
     * @return User
     */
    public User getByEmail(String email) {
    	return userRepository.findByEmail(email);
    }
    
    /**
     * Tester si le mot de passe est correcte pour un email
     * 
     * @param  email    String
     * @param  password String
     * @return User
     */
    public String testEmailAndPassword(String email, String password) {
    	User user = userRepository.findByEmail(email);
    	
    	if(!bCryptPasswordEncoder.matches(password, user.getPassword())) {
    		return JsonGenerator.buildJsonString("error", "Wrong password");
    	}
    	
    	return JsonGenerator.buildJsonString("notice", "Correct password");
    }
    
    /**
     * Recuperer un utilisateur
     * 
     * @param  id Long
     * @return User
     */
    public User getById(Long id) {
    	return userRepository.findOne(id);
    }
    
    /**
     * Cree un utilisateur
     * 
     * @param  user User
     * @return String
     */
    public String create(User user) {
    	
    	// Tester si l'email est deja utilise
    	User u = userRepository.findByEmail(user.getEmail());
    	
    	if(u != null) {
    		return JsonGenerator.buildJsonString("error", "User with same email already exists");
    	}
    	
		user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
        userRepository.save(user);
        return JsonGenerator.buildJsonString("notice", "User added");
    }
    
    /**
     * Met a jour un tilisateur
     * 
     * @param  id        Long
     * @param  newValues User
     * @return String
     */
    public String update(Long id, User newValues) {
    	User user = userRepository.findOne(id);
    	
    	// Tester si le nouveau email est deja utilise
    	User u = userRepository.findByEmail(newValues.getEmail());
    	
    	if(u != null && u.getId() != user.getId()) {
    		return JsonGenerator.buildJsonString("error", "Email already taken");
    	}
    	
    	user.setEmail(newValues.getEmail());
    	user.setPassword(bCryptPasswordEncoder.encode(newValues.getPassword()));
    	user.setLastName(newValues.getLastName());
    	user.setFirstName(newValues.getFirstName());
    	user.setBirthDate(newValues.getBirthDate());
    	user.setDateAdded(newValues.getDateAdded());
    	user.setAdmin(newValues.getAdmin());
        userRepository.save(user);
        return JsonGenerator.buildJsonString("notice", "User updated");
    }
    
    /**
     * Supprimer un utilisateur
     * 
     * @param  id Long
     * @return String
     */
    public String delete(Long id) {
    	User user = userRepository.findOne(id);
    	userRepository.delete(user);
    	return JsonGenerator.buildJsonString("notice", "User deleted");
    }
}
