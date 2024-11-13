package org.gnt.bookreview.bean;

import java.util.List;
import org.gnt.bookreview.entity.Book;
import org.gnt.bookreview.entity.Review;
import org.gnt.bookreview.repository.BookRepository;
import jakarta.ejb.Stateless;
import jakarta.inject.Inject;
import org.apache.logging.log4j.Logger; 
import org.apache.logging.log4j.LogManager;

@Stateless
public class BookBean {
	
	@Inject
	BookRepository bookRepository; 
	private static final Logger logger = LogManager.getLogger();
	
	public List<Book> findAllBooks(){
		logger.info("Entering method findAllBooks");
		return bookRepository.findAll(); 
	}
	
	public void addBook(Book book) {
		logger.info("Entering method findAllBooks");
		bookRepository.save(book);
		logger.info("Book added with success");
	}
	
	public void updateBook(long id,Book updatedBook) {
		logger.info("Entering method updateBook");
		Book oldBook = findBookByID(id);
		updateBookFields(oldBook,updatedBook);
		logger.info("Book updated with success");
	}
	
	public void deleteBook(long id) {
		logger.info("Entering method deleteBook");
		bookRepository.delete(findBookByID(id));
		logger.info("Book deleted with success");
	}

	public List<Review> getReviewsById(long id){
		logger.info("Entering method getReviewsById");
		Book book = findBookByID(id);
		return book.getReviews();
	}
	
	public Book findBookByID(long id) {
		return bookRepository.findById(id); 		
	}
	
	private void updateBookFields(Book oldBook, Book updatedBook) {
		oldBook.setAuthor(updatedBook.getAuthor());
		oldBook.setIsbn(updatedBook.getIsbn());
		oldBook.setAverageRating(updatedBook.getAverageRating());
		oldBook.setTitle(updatedBook.getTitle());
	}
	
}
