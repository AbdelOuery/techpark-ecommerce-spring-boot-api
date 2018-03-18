package com.api.controllers;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.api.entities.Comment;
import com.api.services.CommentService;

@RestController
@RequestMapping("/api/comment")
public class CommentController {
	private final CommentService commentService;
    
    public CommentController(CommentService commentService) {
    	this.commentService = commentService;
    }
	
    @GetMapping("/all")
    public Iterable<Comment> getAll() {
        return commentService.getAll();
    }

    @GetMapping("/product/{id}")
    public Iterable<Comment> getByProduct(@PathVariable("id") Long id) {
    	return commentService.getByProduct(id);
    }

    @GetMapping("/user/{id}")
    public Iterable<Comment> getByUser(@PathVariable("id") Long id) {
    	return commentService.getByUser(id);
    }

    @PostMapping("/create")
    public String create(@RequestBody Comment comment) {
    	return commentService.create(comment);
    }
    
    @GetMapping("/{id}")
    public Comment get(@PathVariable("id") Long id) {
    	return commentService.getById(id);
    }

    @PutMapping("/update/{id}")
    public String update(@PathVariable("id") Long id, @RequestBody Comment newValues) {
    	return commentService.update(id, newValues);
    }

    @DeleteMapping("/delete/{id}")
    public String delete(@PathVariable("id") Long id) {
    	return commentService.delete(id);
    }
}
