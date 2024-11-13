package org.gnt.bookreview.security;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

import jakarta.ws.rs.NameBinding;


@NameBinding
@Retention(RetentionPolicy.RUNTIME)
public @interface JWTValidation {

}
