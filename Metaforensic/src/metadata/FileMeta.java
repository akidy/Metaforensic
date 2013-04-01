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
public class FileMeta {

    private File archivo;
    private static FileMeta instance = new FileMeta();

    /**
     * Inicia variables
     */
    private FileMeta() {
        archivo = null;
    }

    public static FileMeta getInstance() {
        return instance;
    }

    /**
     *
     * @param nombre del archivo sometido a recolección
     */
    public void setNameFile(File archivo) {
        this.archivo = archivo;
    }

    public File getNameFile() {
        return archivo;
    }
}
