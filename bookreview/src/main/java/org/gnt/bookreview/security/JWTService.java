package org.gnt.bookreview.security;

import java.util.Date;
import org.gnt.bookreview.bean.UserBean;
import org.gnt.bookreview.entity.User;
import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;
import jakarta.ejb.EJB;
import jakarta.ws.rs.container.ContainerRequestContext;
import jakarta.ws.rs.core.HttpHeaders;

public class JWTService {
	@EJB
	UserBean userBean; 
	private static final String KEY = "THISISOURSECRETKEY"; 
	 
	 public static String generateToken(User user) {
	        Algorithm algorithm = Algorithm.HMAC256(KEY);
	        return JWT.create()
	                  .withSubject(user.getUsername())
	                  .withClaim("userId", user.getId())
	                  .withExpiresAt(new Date(System.currentTimeMillis() + 3600 * 1000)) //1 hour
	                  .sign(algorithm);
	 }
	 
	 public static String extractToken(ContainerRequestContext requestContext) {
	        String authorizationHeader = requestContext.getHeaderString(HttpHeaders.AUTHORIZATION);
	        if (authorizationHeader != null && authorizationHeader.startsWith("Bearer ")) {
	            return authorizationHeader.substring("Bearer ".length());
	        }
	        return null; 
	 }
	 
	 public static boolean isTokenValid(String token) {
	        try {
	            Algorithm algorithm = Algorithm.HMAC256(KEY);
	            JWTVerifier verifier = JWT.require(algorithm).build();
	            verifier.verify(token);
	            return true;
	        } catch (JWTVerificationException e) {
	            return false; 
	        }
	 }
	 
	 public static String getUsernameFromToken(String token) {
		 if(token==null) {
			 return null; 
		 }
		 DecodedJWT decodedJWT = JWT.decode(token);
		 return decodedJWT.getSubject();
	 }
	 
}
