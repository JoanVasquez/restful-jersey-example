package user.managment.security;

import java.security.Key;

import javax.crypto.spec.SecretKeySpec;

/**
 * @author JoanVasquez
 * Date 19/10/2017
 * A class to generate the JWT key
 */
public class Sha256 {
	/**
	 * @return - The Sha256 key
	 */
	public static Key generateKey() {
		String keyString = "tokengeneratorkey";
		return new SecretKeySpec(keyString.getBytes(), 0, keyString.getBytes().length, "DES");
	}
}