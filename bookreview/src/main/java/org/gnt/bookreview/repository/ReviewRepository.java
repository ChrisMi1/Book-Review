package org.gnt.bookreview.repository;

import java.util.List;
import org.gnt.bookreview.entity.Review;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;

@ApplicationScoped
public class ReviewRepository implements Repository<Review, Long>{
	@PersistenceContext
	EntityManager entityManager; 
	
	
	@Override
	public List<Review> findAll() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Review findById(Long id) {
		// TODO Auto-generated method stub
		return entityManager.find(Review.class, id); 
	}

	@Override
	public void save(Review review) {
		entityManager.persist(review);
		
	}

	@Override
	public void update(Review obj) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void delete(Review review) {
		entityManager.remove(review);
		
	}
	
}
