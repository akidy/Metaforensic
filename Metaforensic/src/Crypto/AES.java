/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Crypto;

import java.io.UnsupportedEncodingException;
import java.nio.charset.Charset;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.security.spec.InvalidParameterSpecException;
import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.KeyGenerator;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

/**
 * AES
 *
 * @author andy737-1
 */
public class AES {

    private final String ALGORITHM = "AES";                               // symmetric algorithm for data encryption  
    private final String MODE = "CBC";
    private final String PADDING = "PKCS5Padding";                        // Padding for symmetric algorithm  
    private final String CIPHER_TRANSFORMATION = ALGORITHM + "/" + MODE + "/" + PADDING;
    private Charset PLAIN_TEXT_ENCODING = Charset.forName("UTF-8");       // character encoding  
    //private final String CRYPTO_PROVIDER = "SunMSCAPI";                 // provider for the crypto  
    private int KEY_SIZE_BITS = 256;                                      /* symmetric key size (128, 192, 256)  
     * if using 256 you must have the Java Cryptography Extension (JCE)  
     * Unlimited Strength Jurisdiction Policy Files installed */

    private int KEY_SIZE_BYTES = KEY_SIZE_BITS / 16;
    private Cipher cipher = Cipher.getInstance(CIPHER_TRANSFORMATION);
    private byte[] ivBytes = new byte[KEY_SIZE_BYTES];
    private SecretKey key;

    public AES()
            throws NoSuchAlgorithmException, NoSuchPaddingException, NoSuchProviderException,
            InvalidParameterSpecException, InvalidKeyException, InvalidAlgorithmParameterException {
        KeyGenerator kgen = KeyGenerator.getInstance(ALGORITHM);
        kgen.init(KEY_SIZE_BITS);
        key = kgen.generateKey();
        cipher.init(Cipher.ENCRYPT_MODE, key);
        ivBytes = cipher.getParameters().getParameterSpec(IvParameterSpec.class).getIV();
    }

    public String getIVAsHex() {
        return Utils.byteArrayToHexString(ivBytes);
    }

    public String getKeyAsHex() {
        return Utils.byteArrayToHexString(key.getEncoded());
    }

    public void setStringToKey(String keyText) throws NoSuchAlgorithmException, UnsupportedEncodingException {
        setKey(keyText.getBytes());
    }

    public void setHexToKey(String hexKey) {
        setKey(Utils.hexStringToByteArray(hexKey));
    }

    public void setKey(byte[] bArray) {
        byte[] bText = new byte[KEY_SIZE_BYTES];
        int end = Math.min(KEY_SIZE_BYTES, bArray.length);
        System.arraycopy(bArray, 0, bText, 0, end);
        key = new SecretKeySpec(bText, ALGORITHM);
    }

    public void setStringToIV(String ivText) {
        setIV(ivText.getBytes());
    }

    public void setHexToIV(String hexIV) {
        setIV(Utils.hexStringToByteArray(hexIV));
    }

    public void setIV(byte[] bArray) {
        byte[] bText = new byte[KEY_SIZE_BYTES];
        int end = Math.min(KEY_SIZE_BYTES, bArray.length);
        System.arraycopy(bArray, 0, bText, 0, end);
        ivBytes = bText;
    }

    public byte[] generateIV() {
        byte[] iv = Utils.getRandomBytes(KEY_SIZE_BYTES);
        return iv;
    }

    public String encrypt(String plaintext, String passphrase)
            throws NoSuchAlgorithmException, UnsupportedEncodingException, InvalidKeyException,
            InvalidAlgorithmParameterException, IllegalBlockSizeException, BadPaddingException {
        if (plaintext.length() == 0) {
            return null;
        }

        setStringToKey(passphrase);

        cipher.init(Cipher.ENCRYPT_MODE, key, new IvParameterSpec(ivBytes));
        byte[] encrypted = cipher.doFinal(plaintext.getBytes(PLAIN_TEXT_ENCODING));
        return Utils.byteArrayToBase64String(encrypted);
    }

    public String decrypt(String ciphertext, String passphrase)
            throws NoSuchAlgorithmException, UnsupportedEncodingException, InvalidKeyException,
            InvalidAlgorithmParameterException, IllegalBlockSizeException, BadPaddingException {
        if (ciphertext.length() == 0 || ciphertext == null) {
            return null;
        }

        setStringToKey(passphrase);

        byte[] dec = Utils.base64StringToByteArray(ciphertext);
        cipher.init(Cipher.DECRYPT_MODE, key, new IvParameterSpec(ivBytes));
        byte[] decrypted = cipher.doFinal(dec);
        return new String(decrypted, PLAIN_TEXT_ENCODING);
    }
}
