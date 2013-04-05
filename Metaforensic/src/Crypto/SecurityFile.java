/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Crypto;

/**
 *
 * @author andy737-1
 */
public class SecurityFile {

    private String pass;
    private String txt;
    private String hash;
    public static SecurityFile instance = new SecurityFile();

    public static SecurityFile getInstance() {
        return instance;
    }

    public void setPass(String pass) {
        this.pass=pass;
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
