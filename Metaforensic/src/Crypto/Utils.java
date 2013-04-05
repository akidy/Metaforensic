/*
 * Credits to http://cryptojs.altervista.org/secretkey/doc/doc_aes_java.html
 * 
 * @Autor Michele Rosica Email: michelerosica@gmail.com
 */
package Crypto;

import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.Random;
import java.util.regex.Pattern;

public class Utils {

    public static String pbkdf2(String password, String salt, int iterationCount, int dkLen)
            throws InvalidKeyException, NoSuchAlgorithmException {
        if (dkLen != 16 && dkLen != 24 && dkLen != 32) {
            dkLen = 16;
        }
        if (iterationCount < 0) {
            iterationCount = 0;
        }

        byte[] _password = password.getBytes();
        byte[] _salt = salt.getBytes();
        byte[] key = PBKDF2.deriveKey(_password, _salt, iterationCount, dkLen);
        return new String(key);
    }

    public static byte[] getRandomBytes(int len) {
        if (len < 0) {
            len = 8;
        }
        Random ranGen = new SecureRandom();
        byte[] aesKey = new byte[len];
        ranGen.nextBytes(aesKey);
        return aesKey;
    }

    public static String byteArrayToHexString(byte[] raw) {
        StringBuilder sb = new StringBuilder(2 + raw.length * 2);
        sb.append("0x");
        for (int i = 0; i < raw.length; i++) {
            sb.append(String.format("%02X", Integer.valueOf(raw[i] & 0xFF)));
        }
        return sb.toString();
    }

    public static byte[] hexStringToByteArray(String hex) {
        Pattern replace = Pattern.compile("^0x");
        String s = replace.matcher(hex).replaceAll("");

        byte[] b = new byte[s.length() / 2];
        for (int i = 0; i < b.length; i++) {
            int index = i * 2;
            int v = Integer.parseInt(s.substring(index, index + 2), 16);
            b[i] = (byte) v;
        }
        return b;
    }

    public static String byteArrayToBase64String(byte[] raw) {
        return new String(Base64Coder.encode(raw));
    }

    public static byte[] base64StringToByteArray(String str) {
        return Base64Coder.decode(str);
    }

    public static String base64_encode(String str) {
        return Base64Coder.encodeString(str);
    }

    public static String base64_decode(String str) {
        return Base64Coder.decodeString(str);
    }
}
