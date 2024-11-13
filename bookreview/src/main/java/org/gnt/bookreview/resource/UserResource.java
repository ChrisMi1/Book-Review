package org.gnt.bookreview.resource;

import org.gnt.bookreview.bean.ReviewBean;
import org.gnt.bookreview.bean.UserBean;
import org.gnt.bookreview.dto.LoginBody;
import org.gnt.bookreview.dto.UpdateUserBody;
import org.gnt.bookreview.entity.User;
import org.gnt.bookreview.exception.EmailAlreadyExists;
import org.gnt.bookreview.exception.UserNotFoundException;
import org.gnt.bookreview.exception.UsernameAlreadyExists;
import org.gnt.bookreview.exception.WrongPasswordException;
import org.gnt.bookreview.security.JWTValidation;
import jakarta.ejb.EJB;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.DELETE;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.PUT;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.Context;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

@Path("/users")
public class UserResource {
	
	@EJB
	UserBean userBean;
	@EJB
	ReviewBean reviewBean; 
	
	@PUT
    @Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	@JWTValidation
    public Response modifyUser(@Context HttpServletRequest requestContext,UpdateUserBody userBody) {
		String username= requestContext.getAttribute("currentUser").toString(); 
		String jwt = userBean.updateCurrentUser(username, userBody);
		return Response.status(Response.Status.OK)
                .entity("{\"message\": \"" + jwt + " \"}")
                .type("application/json")
                .build();
    }
	
	@DELETE
	@JWTValidation
	public Response deleteUser(@Context HttpServletRequest requestContext) {
		String username= requestContext.getAttribute("currentUser").toString(); 
		userBean.deleteCurrentUser(username);
		return Response.status(Response.Status.OK)
                .entity("{\"message\": \" the user deleted successfully \"}")
                .type("application/json")
                .build();
	}
	
	@POST
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public Response registerUser(User user) {
		try{
			userBean.addUser(user);
			return Response.status(Response.Status.OK)
                    .entity("{\"message\": \" the user added successfully \"}")
                    .type("application/json")
                    .build();
		}catch (UsernameAlreadyExists e) {
			return Response.status(Response.Status.CONFLICT)
                    .entity("{\"error\": \"" + e.getMessage() + "\"}")
                    .type("application/json")
                    .build();
			
		}catch (EmailAlreadyExists e) {
			return Response.status(Response.Status.CONFLICT)
            .entity("{\"error\": \"" + e.getMessage() + "\"}")
            .type("application/json")
            .build();
		}
	}
	
	@Path("/login")
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	public Response loginUser(LoginBody loginBody) {
		try {
			String jwt = userBean.authenticateUser(loginBody); 
			return Response.status(Response.Status.OK)
                    .entity("{\"message\": \"" + jwt + " \"}")
                    .type("application/json")
                    .build();
		}catch (UserNotFoundException e) {
			return Response.status(Response.Status.NOT_FOUND)
                    .entity("{\"error\": \"" + e.getMessage() + "\"}")
                    .type("application/json")
                    .build();
		}catch (WrongPasswordException e) {
			return Response.status(Response.Status.UNAUTHORIZED)
                    .entity("{\"error\": \"" + e.getMessage() + "\"}")
                    .type("application/json")
                    .build();
		}
	}
	
	@Path("/{userId}/reviews")
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public Response getReviewsByUserId(@PathParam("userId") long id) {
		return Response.ok(userBean.findReviewsByUserId(id)).build(); 
	}
	
}
