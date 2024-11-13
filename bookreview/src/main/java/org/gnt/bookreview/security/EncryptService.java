package org.gnt.bookreview.security;
import org.mindrot.jbcrypt.BCrypt;
public class EncryptService {
	
	public static String hashPassword(String plainPassword) {
        return BCrypt.hashpw(plainPassword, BCrypt.gensalt(10)); 
    }
	
	public static boolean verifyPassword(String textPassword,String hashPassword) {
		return BCrypt.checkpw(textPassword, hashPassword);
	}
}
