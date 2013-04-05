/*
 * *****************************************************************************
 *    
 * Metaforensic version 1.0 - Análisis forense de metadatos en archivos
 * electrónicos Copyright (C) 2012-2013 TSU. Andrés de Jesús Hernández Martínez,
 * TSU. Idania Aquino Cruz, All Rights Reserved, https://github.com/andy737   
 * 
 * This file is part of Metaforensic.
 *
 * Metaforensic is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * Metaforensic is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with Metaforensic.  If not, see <http://www.gnu.org/licenses/>.
 * 
 * E-mail: andy1818ster@gmail.com
 * 
 * *****************************************************************************
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

        /**
         * * decrypt
         */
        String plaintext = aes.decrypt(sec.getTxt(), key);
        System.out.println(plaintext);
    }
}
