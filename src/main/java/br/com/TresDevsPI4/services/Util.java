package br.com.TresDevsPI4.services;

import java.math.BigInteger;
import java.security.MessageDigest;

public class Util {
	
	
	public static String md5(String senha) {
		try {
			MessageDigest messageDigest = MessageDigest.getInstance("MD5");
			BigInteger hash = new BigInteger(1, messageDigest.digest(senha.getBytes()));
			return hash.toString(16);
		}catch(Exception e) {
			return "";
		}
	}

}
