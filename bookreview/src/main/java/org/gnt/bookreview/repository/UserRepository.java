package org.gnt.bookreview.repository;

import java.util.List;
import java.util.Optional;

import org.gnt.bookreview.entity.User;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.persistence.EntityManager;
import jakarta.persistence.NoResultException;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;
@ApplicationScoped
public class UserRepository implements Repository<User, Long>{
	@PersistenceContext
	private EntityManager entityManager; 
	
	

	@Override
	public List<User> findAll() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public User findById(Long id) {
		// TODO Auto-generated method stub
		return entityManager.find(User.class, id); 
	}
	
	@Override
	public void save(User user) {
		 entityManager.persist(user); 
	}

	@Override
	public void update(User user) {
		entityManager.merge(user); 
	}

	@Override
	public void delete(User user) {
		entityManager.remove(user);
	}
	
	public Optional<User> findByEmail(String email){
		TypedQuery<User> query = entityManager.createQuery("SELECT u FROM User u WHERE u.email = :userEmail",User.class); 
		query.setParameter("userEmail", email); 
		try {
	        return Optional.ofNullable(query.getSingleResult());
	    } catch (NoResultException e) {
	        return Optional.empty(); 
	    }
	}
	
	public Optional<User> findByUsername(String username){
		TypedQuery<User> query = entityManager.createQuery("SELECT u FROM User u WHERE u.username = :userUsername",User.class); 
		query.setParameter("userUsername", username); 
		try {
	        return Optional.ofNullable(query.getSingleResult());
	    } catch (NoResultException e) {
	        return Optional.empty(); 
	    } 
	}
	
	

	
	
}
