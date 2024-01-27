package com.app.controller;
import org.springframework.http.HttpStatus;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.apache.tomcat.util.net.openssl.ciphers.Authentication;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.app.models.Comment;
import com.app.models.Question;
import com.app.models.User;
import com.app.payload.LoginPayload;
import com.app.payload.QuestionPayload;
import com.app.payload.RegisterPayload;
import com.app.repository.QuestionRepository;
import com.app.repository.UserRepository;


import org.springframework.data.mongodb.core.mapping.DBRef;


import jakarta.annotation.PostConstruct;



@RestController
@RequestMapping("/api")
public class AppController {
	private static final Logger log = LoggerFactory.getLogger(AppController.class);
	@Autowired
	private QuestionRepository questionRepo;
	
	@Autowired
	private UserRepository userRepository;
	
	
	@PostMapping("/register")
    public ResponseEntity<?> createUser(@RequestBody RegisterPayload userPayload) {
        
		
    	//log.info("salamun aleyke!");
    	if(userRepository.findByUsername(userPayload.getUsername()) != null) {
    		throw new CustomException("Username already exist");
    	}
        User user = new User();
        user.setUsername(userPayload.getUsername());
        user.setPassword(userPayload.getPassword());
       
        log.info("username: " + userPayload.getUsername() + "password :)))))) : " + userPayload.getPassword());
        
        // Save the user to the database
        User newUser = userRepository.insert(user);
        return ResponseEntity.ok(newUser);
    	
	
	}
	
	@PostMapping("/login")
	public ResponseEntity<?> loginUser(@RequestBody LoginPayload loginUser) {
		        
    	User userDetails = userRepository.findByUsername(loginUser.getUsername()); 
    	
    	if(userDetails == null) {
    		throw new CustomException("Username is wrong");
    	}
    	log.info(loginUser.getPassword());
		log.info(userDetails.getPassword());
    	if(!loginUser.getPassword().equals(userDetails.getPassword()) ) {
    		log.info(loginUser.getPassword());
    		log.info(userDetails.getPassword());
    		log.info("exception thrown, wrong username or password");
    		throw new CustomException("Password is wrong");
    	}
    	
    	log.info("login successful?");    	
    	return ResponseEntity.ok(userDetails);

    }
	
	@PostMapping("/postQuestion")
	public ResponseEntity<?> postQuestions(@RequestBody QuestionPayload questionPayload){
		Question newQuestion = new Question(questionPayload.getQuestion_url(),questionPayload.getSolution_url(),questionPayload.getId(),questionPayload.getCourse(),questionPayload.getSource(),questionPayload.getComments());
		questionRepo.insert(newQuestion);
		return ResponseEntity.ok(newQuestion);
		
	}
	
	
	@GetMapping("/questions")
	public ResponseEntity<List<Question>> returnAllQuestions(){
		List<Question> allQuestions = questionRepo.findAll();
		return ResponseEntity.ok(allQuestions);
	}
	
	@PostMapping("/comment/{questionId}/{username}")
	public ResponseEntity<?> postComment(@PathVariable String questionId, @PathVariable String username, @RequestBody String text){
		Comment comment = new Comment(username,text);
		Optional<Question> optQuestion = questionRepo.findById(questionId);
		if(optQuestion.isPresent()) {
			Question question= optQuestion.get();
			List<Comment> oldComments = question.getComments();
			oldComments.add(comment);
			question.setComments(oldComments); 
			questionRepo.save(question);
			return ResponseEntity.ok(question);
		}
		return ResponseEntity.notFound().build();
	}
	
	
	@ResponseStatus(HttpStatus.BAD_REQUEST)
	public class CustomException extends RuntimeException {
	    private String message;

	    public CustomException(String message) {
	        this.message = message;
	    }

	    public String getMessage() {
	        return message;
	    }
	}
	
	@ExceptionHandler(CustomException.class)
	public ResponseEntity<?> handleUserNotFoundException(CustomException ex) {
	    return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ex.getMessage());
	}
	
	
	
	
	
	
	
	
}
