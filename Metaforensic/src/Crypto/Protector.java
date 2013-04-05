/*
 * Credits to http://cryptojs.altervista.org/secretkey/doc/doc_aes_java.html
 * 
 * @Autor Michele Rosica Email: michelerosica@gmail.com
 */
package Crypto;

import java.io.UnsupportedEncodingException;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.security.spec.InvalidParameterSpecException;
import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;

/**
 * @author Ashwin Kumar
 * 
*/
public class Protector {

    public static Protector instance = new Protector();
    private AES aes;
    private SecurityFile sec;

    public static Protector getInstance() {
        return instance;
    }

    public void main() throws NoSuchAlgorithmException, NoSuchPaddingException, UnsupportedEncodingException, InvalidKeyException, InvalidAlgorithmParameterException, IllegalBlockSizeException, BadPaddingException, NoSuchProviderException, InvalidParameterSpecException {

        sec = SecurityFile.getInstance();
        aes = new AES();
        int keySizeInBits = 256;
        int keySizeInBytes = keySizeInBits / 8;
        // Derive a cryptographic key: PBKDF2  
        String salt = Utils.byteArrayToHexString(Utils.getRandomBytes(8));
        String key = Utils.pbkdf2(sec.getPass(), salt, 1000, keySizeInBytes);
        String encripkey = (StringSHA.getStringMessageDigest(key, StringSHA.SHA512));
        sec.setPass(encripkey);
        // generate IV  
        byte[] ivBytes = aes.generateIV();
        aes.setIV(ivBytes);
        /**
         * * encrypt
         */
        sec.setTxt(aes.encrypt(sec.getTxt(), key));
    }
}
