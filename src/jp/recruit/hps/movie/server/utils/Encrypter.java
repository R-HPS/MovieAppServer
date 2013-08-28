package jp.recruit.hps.movie.server.utils;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class Encrypter{
	
	private static String SHA_512 = "SHA-512";

    public static String getHash(String org){
        if ((org== null)){
            return null;
        }

        MessageDigest md= null;
        try{
            md= MessageDigest.getInstance(SHA_512);
        }
        catch(NoSuchAlgorithmException e){
            return null;
        }

        md.reset();
        md.update(org.getBytes());
        byte[] hash= md.digest();

        StringBuffer sb= new StringBuffer();
        int cnt= hash.length;
        for(int i= 0; i< cnt; i++){
            sb.append(Integer.toHexString( (hash[i]>> 4) & 0x0F ) );
            sb.append(Integer.toHexString( hash[i] & 0x0F ) );
        }
        return sb.toString();
    }
}
