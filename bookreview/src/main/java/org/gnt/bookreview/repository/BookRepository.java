package org.gnt.bookreview.repository;

import java.util.List;
import org.gnt.bookreview.entity.Book;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;

@ApplicationScoped
public class BookRepository implements Repository<Book, Long> {
	
	@PersistenceContext
	EntityManager entityManager; 
	
	@Override
	public List<Book> findAll() {
		TypedQuery<Book> query = entityManager.createQuery("SELECT b FROM Book b",Book.class);
		return query.getResultList();
	}

	@Override
	public Book findById(Long id) {
		return entityManager.find(Book.class, id);   
	}

	@Override
	public void save(Book book) {
		entityManager.persist(book);
	}

	@Override
	public void update(Book obj) {
		// TODO Auto-generated method stub
		
	}
	
	@Override
	public void delete(Book book) {
		entityManager.remove(book);
	}
	
	

}
