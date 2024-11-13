package org.gnt.bookreview.resource;

import org.gnt.bookreview.bean.BookBean;
import org.gnt.bookreview.entity.Book;
import org.gnt.bookreview.security.JWTValidation;

import jakarta.ejb.EJB;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.DELETE;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.PUT;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.QueryParam;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

@Path("/books")
public class BookResource {
	@EJB
	BookBean bookBean;
	
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@JWTValidation
	public Response getAllBooks() {
		return Response.ok(bookBean.findAllBooks()).build();
	}
	
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	@JWTValidation
	public Response addBook(Book book) {
		bookBean.addBook(book);
		return Response.status(Response.Status.OK)
                .entity("{\"message\": \" the book added successfully \"}")
                .type("application/json")
                .build();
	}
	
	@PUT
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	@JWTValidation
	public Response updateBook(@QueryParam("bookId") long bookId,Book book) {
		bookBean.updateBook(bookId,book);
		return Response.status(Response.Status.OK)
                .entity("{\"message\": \" the book updated successfully \"}")
                .type("application/json")
                .build();
	}
	
	@DELETE
	@Produces(MediaType.APPLICATION_JSON)
	@JWTValidation
	public Response deleteBook(@QueryParam("bookId") long bookId) {
		bookBean.deleteBook(bookId);
		return Response.status(Response.Status.OK)
                .entity("{\"message\": \" the book deleted successfully \"}")
                .type("application/json")
                .build();
	}
	
	@Path("/{bookId}/reviews")
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public Response getReviewsByBookId(@PathParam("bookId") long bookId) {
		return Response.ok(bookBean.getReviewsById(bookId)).build(); 
	}
	
}
