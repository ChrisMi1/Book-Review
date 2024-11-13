package org.gnt.bookreview.bean;


import java.util.List;
import java.util.Optional;
import org.gnt.bookreview.dto.LoginBody;
import org.gnt.bookreview.dto.UpdateUserBody;
import org.gnt.bookreview.entity.Review;
import org.gnt.bookreview.entity.User;
import org.gnt.bookreview.exception.EmailAlreadyExists;
import org.gnt.bookreview.exception.UserNotFoundException;
import org.gnt.bookreview.exception.UsernameAlreadyExists;
import org.gnt.bookreview.exception.WrongPasswordException;
import org.gnt.bookreview.repository.UserRepository;
import org.gnt.bookreview.security.EncryptService;
import org.gnt.bookreview.security.JWTService;
import jakarta.ejb.EJB;
import jakarta.ejb.Stateless;
import jakarta.ejb.TransactionAttribute;
import jakarta.ejb.TransactionAttributeType;
import jakarta.inject.Inject;
import org.apache.logging.log4j.Logger; 
import org.apache.logging.log4j.LogManager;

@Stateless
public class UserBean {
	@Inject
	UserRepository userRepository; 
	@EJB
	UserBean selfBean;
	
	private static final Logger logger = LogManager.getLogger();
	
	public void addUser(User user) throws EmailAlreadyExists,UsernameAlreadyExists{
		logger.info("Entering method addUser");
		if(findUserByEmail(user.getEmail()).isPresent()) {
			logger.error("Email already exists",EmailAlreadyExists.class);
			throw new EmailAlreadyExists("A user with that email already exists"); 
		}
		if(findUserByUsername(user.getUsername()).isPresent()) {
			logger.error("Username already exists",UsernameAlreadyExists.class);
			throw new UsernameAlreadyExists("A user with that username already exists"); 
		}
		user.setPassword(EncryptService.hashPassword(user.getPassword()));
		userRepository.save(user);
		logger.info("The user added with username "+user.getUsername()); 
	}
	
	public String authenticateUser(LoginBody loginBody) throws UserNotFoundException,WrongPasswordException {
		logger.info("Entering method authenticateUser");
		Optional<User> userOptional = findUserByUsername(loginBody.getUsername()); 
		if(userOptional.isEmpty()) { 
			logger.error("The user gave wrong username",UserNotFoundException.class);
			throw new UserNotFoundException("Wrong Username"); 
		}
		User user = userOptional.get();
		if(!EncryptService.verifyPassword(loginBody.getPassword(), user.getPassword())) {
			logger.error("The user gave wrong password",WrongPasswordException.class);
			throw new WrongPasswordException("Wrong Password"); 
		}
		logger.info("The user authenicate with success");
		return JWTService.generateToken(userOptional.get()); 
	}
	
	public String updateCurrentUser(String username,UpdateUserBody updateUserBody) {
		logger.info("Entering method updateCurrentUser");
		Optional<User> userOptional = selfBean.findUserByUsername(username); 
		User user = userOptional.get(); 
		updateTheFieldsOfCurrentUser(user,updateUserBody);
		userRepository.update(user);
		logger.info("The user updated with id:"+user.getId());
		return JWTService.generateToken(user);
	}
	
	public void deleteCurrentUser(String username) {
		logger.info("Entering method deleteCurrentUser");
		Optional<User> userOptional = findUserByUsername(username); 
		User user = userOptional.get();
		userRepository.delete(user);
		logger.info("The user has deleted with id: "+user.getId());
	}
	
	public List<Review> findReviewsByUserId(long id) {
		logger.info("Entering method findReviewsByUserId");
		User user = userRepository.findById(id);
		return user.getReviews();
	}
	
	@TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
	public Optional<User> findUserByUsername(String username) {
		logger.info("Entering method findUserByUsername");
		return userRepository.findByUsername(username);
	}
	
	private Optional<User> findUserByEmail(String email) {
		return userRepository.findByEmail(email);
	}
	
	private void updateTheFieldsOfCurrentUser(User user,UpdateUserBody updateUserBody) {
		user.setUsername(updateUserBody.getUsername());
		user.setPassword(EncryptService.hashPassword(updateUserBody.getPassword()));
		user.setEmail(updateUserBody.getEmail());
	}
}
