package org.gnt.bookreview.security;

import org.gnt.bookreview.bean.UserBean;
import org.gnt.bookreview.exception.InvalidJWTException;
import jakarta.ejb.EJB;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.ws.rs.container.ContainerRequestContext;
import jakarta.ws.rs.container.ContainerRequestFilter;
import jakarta.ws.rs.core.Context;
import jakarta.ws.rs.ext.Provider;

@JWTValidation
@Provider
public class JWTValidationFilter implements ContainerRequestFilter {
	
	@Context
	HttpServletRequest request;
	@EJB
	UserBean userBean; 
	
	@Override
	public void filter(ContainerRequestContext requestContext) {
		 String token = JWTService.extractToken(requestContext);
		 String usernameOfCurrentUser = JWTService.getUsernameFromToken(token);
		 if( token==null || !JWTService.isTokenValid(token) || userBean.findUserByUsername(usernameOfCurrentUser).isEmpty()) {
			 throw new InvalidJWTException("Invalid token"); 
		 }
		 request.setAttribute("currentUser", usernameOfCurrentUser);
	}
	
}
