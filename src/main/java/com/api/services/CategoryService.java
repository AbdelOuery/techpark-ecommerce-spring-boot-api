package com.api.services;

import org.springframework.stereotype.Service;

import com.api.JsonGenerator;
import com.api.entities.Category;
import com.api.repositories.CategoryRepository;

@Service
public class CategoryService {
	private final CategoryRepository categoryRepository;

	public CategoryService(CategoryRepository categoryRepository) {
		this.categoryRepository = categoryRepository;
	}

    /**
     * Recuperer toutes les categories
     * 
     * @return Iterable<Category>
     */
    public Iterable<Category> getAll() {
        return categoryRepository.findAll();
    }

    /**
     * Recuperer une seule categorie
     * 
     * @param  id Long
     * @return Category
     */
    public Category getById(Long id) {
    	return categoryRepository.findOne(id);
    }
    
    /**
     * Cree une categorie
     * 
     * @param  category Category
     * @return String
     */
    public String create(Category category) {
    	categoryRepository.save(category);
    	return JsonGenerator.buildJsonString("notice", "Category added");
    }

    /**
     * Met a jour une categorie
     * 
     * @param  id        Long
     * @param  newValues Category
     * @return String
     */
    public String update(Long id, Category newValues) {
		Category category = categoryRepository.findOne(id);
		category.setTitle(newValues.getTitle());
    	categoryRepository.save(category);
    	return JsonGenerator.buildJsonString("notice", "Category updated");
    }

    /**
     * Supprimer une categorie
     * 
     * @param  id Long
     * @return String
     */
	public String delete(Long id) {
    	Category category = categoryRepository.findOne(id);
    	categoryRepository.delete(category);
    	return JsonGenerator.buildJsonString("notice", "Category deleted");
    }
}
