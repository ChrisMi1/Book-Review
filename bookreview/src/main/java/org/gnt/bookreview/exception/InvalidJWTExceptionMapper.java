package org.gnt.bookreview.exception;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.ext.ExceptionMapper;
import jakarta.ws.rs.ext.Provider;

@Provider
public class InvalidJWTExceptionMapper implements ExceptionMapper<InvalidJWTException> {

    @Override
    public Response toResponse(InvalidJWTException ex) {
        return Response.status(Response.Status.UNAUTHORIZED)
                .entity("{\"error\": \"" + ex.getMessage() + "\"}")
                .type("application/json")
                .build();
    }
}