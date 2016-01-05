/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.bowlink.rr.security;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;
import java.security.Key;
import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;

public class AESCrypt 
{
    private static final String ALGORITHM = "AES";
    
    public static String encrypt(String value, String passPhrase) throws Exception
    {
    	Key key = generateKey(passPhrase);
        Cipher cipher = Cipher.getInstance(AESCrypt.ALGORITHM);
        cipher.init(Cipher.ENCRYPT_MODE, key);
        byte [] encryptedByteValue = cipher.doFinal(value.getBytes("utf-8"));
        String encryptedValue64 = new BASE64Encoder().encode(encryptedByteValue);
        return encryptedValue64;
               
    }
    
    public static String decrypt(String value, String passPhrase) throws Exception
    {
        Key key = generateKey(passPhrase);
        Cipher cipher = Cipher.getInstance(AESCrypt.ALGORITHM);
        cipher.init(Cipher.DECRYPT_MODE, key);
        byte [] decryptedValue64 = new BASE64Decoder().decodeBuffer(value);
        byte [] decryptedByteValue = cipher.doFinal(decryptedValue64);
        String decryptedValue = new String(decryptedByteValue,"utf-8");
        return decryptedValue;
                
    }
    
    private static Key generateKey(String passPhrase) throws Exception 
    {
        Key key = new SecretKeySpec(passPhrase.getBytes(), ALGORITHM);
        return key;
    }
}