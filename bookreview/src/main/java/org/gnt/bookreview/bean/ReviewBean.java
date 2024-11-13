package org.gnt.bookreview.bean;



import java.sql.Timestamp;

import org.gnt.bookreview.entity.Book;
import org.gnt.bookreview.entity.Review;
import org.gnt.bookreview.entity.User;
import org.gnt.bookreview.exception.NotOwnerOfReview;
import org.gnt.bookreview.repository.ReviewRepository;
import org.apache.logging.log4j.Logger; 
import org.apache.logging.log4j.LogManager;
import jakarta.ejb.EJB;
import jakarta.ejb.Stateless;
import jakarta.inject.Inject;

@Stateless
public class ReviewBean {
	
	@Inject
	ReviewRepository reviewRepository; 
	@EJB
	UserBean userBean; 
	@EJB
	BookBean bookBean;
	private static final Logger logger = LogManager.getLogger();
	
	public void addReview(String username,long bookId,Review review) {
		logger.info("Entering method addReview");
		User user = userBean.findUserByUsername(username).get();
		Book book = bookBean.findBookByID(bookId); 
		review.setBook(book);
		review.setUser(user);
		review.setReviewDate(new Timestamp(System.currentTimeMillis()));
		reviewRepository.save(review);
		logger.info("The review added with success by user with username: "+username);
	}
	
	public void updateReview(String username,long reviewId,Review newReview) throws NotOwnerOfReview {
		logger.info("Entering method updateReview");
		Review oldReview = findReviewById(reviewId);
		if(!isUserTheSame(oldReview,username)) {
			logger.error("Not the owner of the review",NotOwnerOfReview.class);
			throw new NotOwnerOfReview("You can't change a review you didnt publish");
		}
		oldReview.setComment(newReview.getComment());
		oldReview.setRating(newReview.getRating());
		oldReview.setReviewDate(new Timestamp(System.currentTimeMillis()));
		logger.info("The review updated with success by user with username: "+username);
	}
	
	public void deleteReview(String username,long reviewId) throws NotOwnerOfReview{
		logger.info("Entering method deleteReview");
		Review oldReview = findReviewById(reviewId);
		if(!isUserTheSame(oldReview,username)) {
			logger.error("Not the owner of the review",NotOwnerOfReview.class);
			throw new NotOwnerOfReview("You can't change a review you didnt publish");
		}
		reviewRepository.delete(oldReview);
		logger.info("The review deleted with success by user with username: "+username);
	}
	
	public Review findReviewById(long id) {
		return reviewRepository.findById(id); 
	}
	
	private boolean isUserTheSame(Review oldReview,String username ) {
		return oldReview.getUser().getUsername().equals(username);
		
	}
	
	
}
