/*
 * Credits to http://cryptojs.altervista.org/secretkey/doc/doc_aes_java.html
 * 
 * @Autor Michele Rosica Email: michelerosica@gmail.com
 */
package Crypto;

public class SecurityFile {

    private String pass;
    private String txt;
    private String hash;
    public static SecurityFile instance = new SecurityFile();

    public static SecurityFile getInstance() {
        return instance;
    }

    public void setPass(String pass) {
        this.pass = pass;
    }

    public String getPass() {
        return pass;
    }

    public void setTxt(String txt) {
        this.txt = txt;
    }

    public String getTxt() {
        return txt;
    }

    public void setPrivate(String txt) {
        this.hash = hash;
    }

    public String getPrivate() {
        return hash;
    }
}
