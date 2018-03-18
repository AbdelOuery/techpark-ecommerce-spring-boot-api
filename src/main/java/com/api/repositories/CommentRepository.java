package com.api.repositories;

import org.springframework.data.repository.CrudRepository;

import com.api.entities.Comment;

public interface CommentRepository extends CrudRepository<Comment, Long> { }
