package org.gnt.bookreview.resource;

import org.gnt.bookreview.bean.ReviewBean;
import org.gnt.bookreview.entity.Review;
import org.gnt.bookreview.exception.NotOwnerOfReview;
import org.gnt.bookreview.security.JWTValidation;

import jakarta.ejb.EJB;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.DELETE;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.PUT;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.Context;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

@Path("/reviews")
public class ReviewResource {
	
	@EJB
	ReviewBean reviewBean; 
	
	@Path("/{bookId}")
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	@JWTValidation
	public Response addReview(@PathParam("bookId") long bookId,@Context HttpServletRequest requestContext,Review review) {
		String username = requestContext.getAttribute("currentUser").toString(); 
		reviewBean.addReview(username, bookId, review); 
		return Response.status(Response.Status.OK)
                .entity("{\"message\": \" the review added successfully \"}")
                .type("application/json")
                .build();
	}
	
	@Path("/{reviewId}")
	@PUT
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	@JWTValidation
	public Response updateReview(@PathParam("reviewId") long reviewId,@Context HttpServletRequest requestContext,Review review) {
		String username = requestContext.getAttribute("currentUser").toString(); 
		try{
			reviewBean.updateReview(username, reviewId, review);
			return Response.status(Response.Status.OK)
            .entity("{\"message\": \" the review modified successfully \"}")
            .type("application/json")
            .build();
		}catch (NotOwnerOfReview e) {
			return Response.status(Response.Status.UNAUTHORIZED)
                    .entity("{\"error\": \"" + e.getMessage() + "\"}")
                    .type("application/json")
                    .build();
		}
	}
	
	@Path("/{reviewId}")
	@DELETE
	@Produces(MediaType.APPLICATION_JSON)
	@JWTValidation
	public Response deleteReview(@PathParam("reviewId") long reviewId,@Context HttpServletRequest requestContext) {
		String username = requestContext.getAttribute("currentUser").toString(); 
		try{
			reviewBean.deleteReview(username, reviewId); 
			return Response.status(Response.Status.OK)
            .entity("{\"message\": \" the review deleted successfully \"}")
            .type("application/json")
            .build();
		}catch (NotOwnerOfReview e) {
			return Response.status(Response.Status.UNAUTHORIZED)
                    .entity("{\"error\": \"" + e.getMessage() + "\"}")
                    .type("application/json")
                    .build();
		}
	}
	
	
}
