/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package metadata;

import java.io.File;

/**
 *
 * @author andy737-1
 */
public class Hash {

    private String hash;
    private File archivo;

    public Hash() {
        hash = "";
        archivo = null;
    }

    public String getHash() {
        return hash;
    }

    public File getFile() {
        return archivo;
    }

    public void setHash(String hash) {
        this.hash = hash;
    }

    public void setFile(File archivo) {
        this.archivo = archivo;
    }
}
