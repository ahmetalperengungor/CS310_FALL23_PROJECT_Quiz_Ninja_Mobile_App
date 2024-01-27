package com.app.repository;
import java.util.List;
import java.util.Optional;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.app.models.Question;

@Repository
public interface QuestionRepository extends MongoRepository<Question, String>{
	public List<Question> findByCourse(String course);
	public List<Question> findAll();
}
