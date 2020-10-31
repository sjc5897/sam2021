package com.sam2021.security;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * Simple class to implement basic hashing
 * Language: Java 13
 * Framework: Spring
 * Author: Stephen Cook <sjc5897@rit.edu>
 * Created: 10/24/20
 * Last Edit: 10/24/20
 */
public class Hasher {

    /**
     * Static Method used to hash passwords
     * @param pw String plan text password
     * @return   Hashed password
     */
    public static String hashPass(String pw){
        // TODO: Implement Salting
        try{
            MessageDigest md = MessageDigest.getInstance("SHA-512");
            byte[] messageDigest = md.digest(pw.getBytes());

            BigInteger no = new BigInteger(1,messageDigest);
            String hashtext = no.toString(16);
            while (hashtext.length() < 32){
                hashtext = "0" + hashtext;
            }
            return hashtext;
        }catch (NoSuchAlgorithmException e){
            throw new RuntimeException(e);
        }
    }
}
