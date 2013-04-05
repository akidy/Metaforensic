/*Clase encargada de la encriptación SHA-512 para validación de contraseña de inicio se sesión.
 *Autor: Andrés de Jesús Hernádez Martínez
 *Version: 0.1
 *Fecha de creación: 18 de enero de 2012
 *Fecha de ultima modificación: 18 de enero de 2012
 */
package Crypto;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class StringSHA {

    public static String SHA512 = "SHA-512";

    private static String toHexadecimal(byte[] digest) {
        String hash = "";
        for (byte aux : digest) {
            int b = aux & 0xff;
            if (Integer.toHexString(b).length() == 1) {
                hash += "0";
            }
            hash += Integer.toHexString(b);
        }
        return hash;
    }

    public static String getStringMessageDigest(String message, String algorithm) throws NoSuchAlgorithmException {
        byte[] digest;
        byte[] buffer = message.getBytes();
        MessageDigest messageDigest = MessageDigest.getInstance(algorithm);
        messageDigest.reset();
        messageDigest.update(buffer);
        digest = messageDigest.digest();
        return toHexadecimal(digest);
    }
}
